package elvis.chat.playgroundchatserver.model

import elvis.chat.playgroundchatserver.service.ChatService
import org.springframework.web.socket.WebSocketSession

class ChatRoom(
    val roomId: String,
    val name: String,
    private val sessions: MutableSet<WebSocketSession> = mutableSetOf(),
) {
    fun handleActions(session: WebSocketSession, chatMessage: ChatMessage, chatService: ChatService) {
        when(chatMessage.type) {
            ChatMessage.MessageType.ENTER -> {
                sessions.add(session)
                val message = ChatMessage(chatMessage.type,
                    chatMessage.roomId,
                    chatMessage.sender,
                    "${chatMessage.sender + "님이 입장했습니다."}")
                sendMessage(message, chatService)
            }
            ChatMessage.MessageType.TALK -> {
                sendMessage(chatMessage, chatService)
            }
        }
    }

    private fun <T> sendMessage(message: T, chatService: ChatService) {
        sessions.parallelStream().forEach { session -> chatService.sendMessage(session, message) }
    }
}