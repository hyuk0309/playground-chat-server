package elvis.chat.playgroundchatserver.handler

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

private val log = KotlinLogging.logger { }

@Component
class WebSocketChatHandler : TextWebSocketHandler() {
//    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
//        val payload = message.payload
//        log.info { "payload : $payload" }
//        val textMessage = TextMessage("Welcome My First Websocket chatting server. :)")
//        session.sendMessage(textMessage)
//    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        log.info { "payload : $payload" }
        val textMessage = TextMessage("Welcome My First Websocket chatting server. :)")
        session.sendMessage(textMessage)
    }
}
