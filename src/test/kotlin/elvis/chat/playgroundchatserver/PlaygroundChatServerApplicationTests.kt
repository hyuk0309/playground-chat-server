package elvis.chat.playgroundchatserver

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.test.context.web.WebAppConfiguration

@SpringBootTest
@Import(ServletWebServerApplicationContext::class)
class PlaygroundChatServerApplicationTests {
    @Test
    fun contextLoads() {
    }
}
