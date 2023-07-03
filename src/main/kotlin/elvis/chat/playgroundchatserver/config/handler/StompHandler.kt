package elvis.chat.playgroundchatserver.config.handler

import elvis.chat.playgroundchatserver.service.JwtTokenProvider
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class StompHandler(
    private val jwtTokenProvider: JwtTokenProvider,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        // verify jwt token
        if (StompCommand.CONNECT == accessor.command) {
            jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"))
        }
        return message
    }
}
