package elvis.chat.playgroundchatserver.controller

import elvis.chat.playgroundchatserver.model.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val messagingTemplate: SimpMessageSendingOperations,
) {
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage) {
        when (message.type) {
            ChatMessage.MessageType.ENTER -> {
                val enterMessage =
                    ChatMessage(message.type, message.roomId, message.sender, "${message.sender}님이 입장했습니다.")
                messagingTemplate.convertAndSend("/sub/chat/room/${enterMessage.roomId}", enterMessage)
            }
            ChatMessage.MessageType.TALK -> {
                messagingTemplate.convertAndSend("/sub/chat/room/${message.roomId}", message)
            }
        }
    }
}
