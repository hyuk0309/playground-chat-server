package elvis.chat.playgroundchatserver.config.handler

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import elvis.chat.playgroundchatserver.service.ChatService
import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import mu.KotlinLogging
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.security.Principal

@Component
class StompHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatService: ChatService,
) : ChannelInterceptor {

    private val log = KotlinLogging.logger { }

    /**
     * interceptor before send message to message handler
     */
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        if (StompCommand.CONNECT == accessor.command) {
            val jwtToken = accessor.getFirstNativeHeader("token")
            log.info { "CONNECT $jwtToken" }

            jwtTokenProvider.validateToken(jwtToken)
        } else if (StompCommand.SUBSCRIBE == accessor.command) {
            val roomId = chatService.getRoomId(message.headers["simpDestination"] as? String ?: "InvalidRoomId")
            val sessionId = message.headers["simpSessionId"] as String
            chatRoomRepository.setUserEnterInfo(sessionId, roomId)

            chatRoomRepository.plusUserCount(roomId)

            val name = (message.headers["simpUser"] as? Principal)?.name ?: "UnknownUser"
            chatService.sendChatMessage(ChatMessage(
                type = ChatMessage.MessageType.ENTER,
                roomId = roomId,
                sender = name))
            log.info { "SUBSCRIBED $name, $roomId" }
        } else if (StompCommand.DISCONNECT == accessor.command) {
            val sessionId = message.headers["simpSessionId"] as String
            val roomId = chatRoomRepository.getUserEnterRoomId(sessionId) ?: throw IllegalArgumentException()

            chatRoomRepository.minusUserCount(roomId)

            val name = (message.headers["simpUser"] as? Principal)?.name ?: "UnknownUser"
            chatService.sendChatMessage(ChatMessage(
                type = ChatMessage.MessageType.QUIT,
                roomId = roomId,
                sender = name
            ))
            chatRoomRepository.removeUserEnterInfo(sessionId)
            log.info { "DISCONNECTED $sessionId, $roomId" }
        }
        return message
    }
}
