package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import elvis.chat.playgroundchatserver.service.ChatService
import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatService: ChatService,
) {
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage, @Header("token") token: String) {
        val nickname = jwtTokenProvider.getUserNameFromJwt(token)

        message.sender = nickname
        message.userCount = chatRoomRepository.getUserCount(message.roomId)
        chatService.sendChatMessage(message)
    }
}
