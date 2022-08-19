package net.murzikov.webchat.common.models

@JvmInline
value class ChatMessageId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ChatMessageId("")
    }
}
