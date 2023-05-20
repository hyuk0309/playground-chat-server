package elvis.chat.playgroundchatserver.config

import elvis.chat.playgroundchatserver.handler.WebSocketChatHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(myHandler(), "/ws/chat").setAllowedOrigins("*")
    }

    @Bean
    fun myHandler(): WebSocketHandler = WebSocketChatHandler()
}
