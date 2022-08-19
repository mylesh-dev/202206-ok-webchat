package net.murzikov.webchat.common.models

@JvmInline
value class ChatlUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ChatlUserId("")
    }
}
