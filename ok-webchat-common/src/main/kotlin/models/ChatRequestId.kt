package net.murzikov.webchat.common.models

@JvmInline
value class ChatRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ChatRequestId("")
    }
}
