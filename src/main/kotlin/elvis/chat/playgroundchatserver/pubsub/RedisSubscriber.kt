package elvis.chat.playgroundchatserver.pubsub

import com.fasterxml.jackson.databind.ObjectMapper
import elvis.chat.playgroundchatserver.model.ChatMessage
import mu.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class RedisSubscriber(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val messagingTemplate: SimpMessageSendingOperations,
) : MessageListener {

    private val log = KotlinLogging.logger { }

    /**
     * handle message when a message is published in redis
     */
    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val publishedMessage = redisTemplate.stringSerializer.deserialize(message.body) as String
            val roomMessage = objectMapper.readValue(publishedMessage, ChatMessage::class.java)
            messagingTemplate.convertAndSend("/sub/chat/room/${roomMessage.roomId}", roomMessage)
        } catch (e: Exception) {
            log.error { e.message }
        }
    }
}
