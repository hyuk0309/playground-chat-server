package elvis.chat.playgroundchatserver.repo

import elvis.chat.playgroundchatserver.model.ChatRoom
import jakarta.annotation.PostConstruct
import jakarta.annotation.Resource
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository

// redis cache keys
private const val CHAT_ROOMS = "CHAT_ROOM"
private const val USER_COUNT = "USER_COUNT"
private const val ENTER_INFO = "ENTER_INFO"

@Repository
class ChatRoomRepository {

    @Resource(name = "redisTemplate")
    private lateinit var hashOpsChatRoom: HashOperations<String, String, ChatRoom>

    @Resource(name = "redisTemplate")
    private lateinit var hashOpsEnterInfo: HashOperations<String, String, String>

    @Resource(name = "redisTemplate")
    private lateinit var valueOps: ValueOperations<String, String>

    fun findAllRoom(): List<ChatRoom> = hashOpsChatRoom.values(CHAT_ROOMS)

    fun findRoomById(id: String): ChatRoom = hashOpsChatRoom.get(CHAT_ROOMS, id)!!

    fun createChatRoom(name: String): ChatRoom {
        val newChatRoom = ChatRoom(name)
        hashOpsChatRoom.put(CHAT_ROOMS, newChatRoom.roomId, newChatRoom)
        return newChatRoom
    }

    fun setUserEnterInfo(sessionId: String, roomId: String) = hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId)

    fun getUserEnterRoomId(sessionId: String) = hashOpsEnterInfo.get(ENTER_INFO, sessionId)

    fun removeUserEnterInfo(sessionId: String) = hashOpsEnterInfo.delete(ENTER_INFO, sessionId)

    fun getUserCount(roomId: String) = (valueOps.get("${USER_COUNT}_${roomId}") ?: "0").toLong()

    fun plusUserCount(roomId: String) = valueOps.increment("${USER_COUNT}_${roomId}") ?: 0L

    fun minusUserCount(roomId: String): Long {
        val count = valueOps.decrement("${USER_COUNT}_${roomId}") ?: 0L
        return if (count <= 0) 0 else count
    }
}
