package elvis.chat.playgroundchatserver.model

import java.io.Serializable
import java.util.*

class ChatRoom(val name: String) : Serializable {

    companion object {
        private const val serialVersionUID = 6494678977089006639L
    }

    val roomId = UUID.randomUUID().toString()
}