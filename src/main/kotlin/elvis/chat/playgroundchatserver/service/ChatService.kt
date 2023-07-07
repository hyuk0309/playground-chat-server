package elvis.chat.playgroundchatserver.service

import elvis.chat.playgroundchatserver.model.ChatMessage
import elvis.chat.playgroundchatserver.repo.ChatRoomRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val channelTopic: ChannelTopic,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val chatRoomRepository: ChatRoomRepository,
) {

    fun getRoomId(destination: String): String {
        val lastIndex = destination.lastIndexOf('/')
        return if (lastIndex != -1) {
            destination.substring(lastIndex + 1)
        } else {
            ""
        }
    }

    fun sendChatMessage(chatMessage: ChatMessage) {
        chatMessage.userCount = chatRoomRepository.getUserCount(chatMessage.roomId)
        if (chatMessage.type == ChatMessage.MessageType.ENTER) {
            chatMessage.message = "${chatMessage.sender}님이 방에 입장했습니다."
            chatMessage.sender = "[알림]"
        } else if (chatMessage.type == ChatMessage.MessageType.QUIT) {
            chatMessage.message = "${chatMessage.sender}님이 방에서 나갔습니다."
            chatMessage.sender = "[알림]"
        }
        redisTemplate.convertAndSend(channelTopic.topic, chatMessage)
    }
}
