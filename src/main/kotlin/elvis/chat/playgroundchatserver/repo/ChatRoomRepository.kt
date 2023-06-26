package elvis.chat.playgroundchatserver.repo

import elvis.chat.playgroundchatserver.model.ChatRoom
import elvis.chat.playgroundchatserver.pubsub.RedisSubscriber
import jakarta.annotation.PostConstruct
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository

private val CHAT_ROOMS = "CHAT_ROOM"

@Repository
class ChatRoomRepository(
    private val redisMessageListener: RedisMessageListenerContainer,
    private val redisSubscriber: RedisSubscriber,
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    private lateinit var opsHashChatRoom: HashOperations<String, String, ChatRoom>
    private lateinit var topics: MutableMap<String, ChannelTopic>

    @PostConstruct
    private fun init() {
        opsHashChatRoom = redisTemplate.opsForHash()
        topics = HashMap()
    }

    fun findAllRoom(): List<ChatRoom> = opsHashChatRoom.values(CHAT_ROOMS)

    fun findRoomById(id: String): ChatRoom = opsHashChatRoom.get(CHAT_ROOMS, id)!!

    fun createChatRoom(name: String): ChatRoom {
        val newChatRoom = ChatRoom(name)
        opsHashChatRoom.put(CHAT_ROOMS, newChatRoom.roomId, newChatRoom)
        return newChatRoom
    }

    fun enterChatRoom(roomId: String) {
        var topic = topics[roomId]
        if (topic == null) {
            topic = ChannelTopic(roomId)
        }
        redisMessageListener.addMessageListener(redisSubscriber, topic)
        topics[roomId] = topic
    }

    fun getTopic(roomId: String): ChannelTopic = topics[roomId]!!
}
