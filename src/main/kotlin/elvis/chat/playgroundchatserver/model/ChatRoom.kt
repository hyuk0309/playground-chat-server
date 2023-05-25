package elvis.chat.playgroundchatserver.model

import java.util.*

class ChatRoom(val name: String) {
    val roomId = UUID.randomUUID().toString()
}