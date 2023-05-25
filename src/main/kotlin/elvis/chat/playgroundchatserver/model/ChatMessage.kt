package elvis.chat.playgroundchatserver.model

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    val sender: String,
    val message: String?,
) {
    enum class MessageType {
        ENTER, TALK
    }
}
