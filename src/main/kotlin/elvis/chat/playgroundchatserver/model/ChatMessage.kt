package elvis.chat.playgroundchatserver.model

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    var sender: String?,
    var message: String? = null,
    var userCount: Long? = null,
) {
    enum class MessageType {
        ENTER, QUIT, TALK
    }
}
