package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.pubsub.RedisPublisher
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisPublisher: RedisPublisher,
    private val chatRoomRepository: ChatRoomRepository,
) {
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage, @Header("token") token: String) {
        val nickname = jwtTokenProvider.getUserNameFromJwt(token)

        if (ChatMessage.MessageType.ENTER == message.type) {
            chatRoomRepository.enterChatRoom(message.roomId)
            message.message = "${nickname}님이 입장하셨습니다."
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId), message)
    }
}
