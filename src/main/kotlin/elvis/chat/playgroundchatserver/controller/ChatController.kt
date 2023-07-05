package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val jwtTokenProvider: JwtTokenProvider,
    private val channelTopic: ChannelTopic,
) {
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage, @Header("token") token: String) {
        val nickname = jwtTokenProvider.getUserNameFromJwt(token)

        message.sender = nickname
        if (ChatMessage.MessageType.ENTER == message.type) {
            message.sender = "[알림]"
            message.message = "${nickname}님이 입장하셨습니다."
        }
        redisTemplate.convertAndSend(channelTopic.topic, message)
    }
}
