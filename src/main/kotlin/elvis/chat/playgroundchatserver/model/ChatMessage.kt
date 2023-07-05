package elvis.chat.playgroundchatserver.model

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    var sender: String?,
    var message: String?,
) {
    enum class MessageType {
        ENTER, TALK
    }
}
