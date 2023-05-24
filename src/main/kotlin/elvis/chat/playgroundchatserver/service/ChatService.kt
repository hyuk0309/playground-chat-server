package elvis.chat.playgroundchatserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import elvis.chat.playgroundchatserver.model.ChatRoom
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.UUID

private val log = KotlinLogging.logger {  }

@Service
class ChatService(
    private val objectMapper: ObjectMapper
) {
    private lateinit var chatRooms: MutableMap<String, ChatRoom>

    @PostConstruct
    private fun init() {
        chatRooms = LinkedHashMap()
    }

    fun findAllRoom(): List<ChatRoom> {
        return chatRooms.values.toList()
    }

    fun findRoomById(roomId: String): ChatRoom {
        return chatRooms[roomId]!!
    }

    fun createRoom(name: String): ChatRoom {
        val randomId = UUID.randomUUID().toString()
        val chatRoom = ChatRoom(randomId, name)
        chatRooms[randomId] = chatRoom
        return chatRoom
    }

    fun <T> sendMessage(session: WebSocketSession, message: T) {
        try {
            session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
        } catch (e: IOException) {
            log.error(e.message, e)
        }
    }
}