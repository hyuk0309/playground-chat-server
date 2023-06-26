package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.pubsub.RedisPublisher
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val redisPublisher: RedisPublisher,
    private val chatRoomRepository: ChatRoomRepository,
) {
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage) {
        if (ChatMessage.MessageType.ENTER == message.type) {
            chatRoomRepository.enterChatRoom(message.roomId)
            redisPublisher.publish(
                chatRoomRepository.getTopic(message.roomId),
                ChatMessage(message.type, message.roomId, message.sender, "${message.sender}님이 입장하셨습니다.")
            )
            return
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId), message)
    }
}
