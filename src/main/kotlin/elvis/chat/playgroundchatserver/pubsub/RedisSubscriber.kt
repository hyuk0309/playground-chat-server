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
    private val messagingTemplate: SimpMessageSendingOperations,
) {
    private val log = KotlinLogging.logger { }

    /**
     * handle message when a message is published in redis
     */
    fun sendMessage(publishMessage: String) {
        try {
            val chatMessage = objectMapper.readValue(publishMessage, ChatMessage::class.java)
            messagingTemplate.convertAndSend("/sub/chat/room/${chatMessage.roomId}", chatMessage)
        } catch (e: Exception) {
            log.error { "Exception $e" }
        }
    }
}
