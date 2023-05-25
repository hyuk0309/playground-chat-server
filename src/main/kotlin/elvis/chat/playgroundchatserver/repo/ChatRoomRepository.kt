package elvis.chat.playgroundchatserver.repo

import elvis.chat.playgroundchatserver.model.ChatRoom
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepository {
    private lateinit var chatRoomMap: MutableMap<String, ChatRoom>

    @PostConstruct
    private fun init() {
        chatRoomMap = LinkedHashMap()
    }

    fun findAllRoom(): List<ChatRoom> = chatRoomMap.values.toList().reversed()

    fun findRoomById(id: String): ChatRoom = chatRoomMap[id] ?: throw IllegalArgumentException()

    fun createChatRoom(name: String): ChatRoom {
        val newChatRoom = ChatRoom(name)
        chatRoomMap[newChatRoom.roomId] = newChatRoom
        return newChatRoom
    }
}
