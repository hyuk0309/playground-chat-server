package elvis.chat.playgroundchatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlaygroundChatServerApplication

fun main(args: Array<String>) {
    runApplication<PlaygroundChatServerApplication>(*args)
}
