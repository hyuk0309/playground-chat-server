package elvis.chat.playgroundchatserver.model

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    val sender: String?,
    var message: String?,
) {
    enum class MessageType {
        ENTER, TALK
    }
}
