package elvis.chat.playgroundchatserver.handler

import com.fasterxml.jackson.databind.ObjectMapper
import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.service.ChatService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

private val log = KotlinLogging.logger { }

@Component
class WebSocketChatHandler(
    private val objectMapper: ObjectMapper,
    private val chatService: ChatService,
) : TextWebSocketHandler() {
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        log.info { "payload : $payload" }
        val chatMessage = objectMapper.readValue(payload, ChatMessage::class.java)
        val room = chatService.findRoomById(chatMessage.roomId)
        room.handleActions(session, chatMessage, chatService)
    }
}
