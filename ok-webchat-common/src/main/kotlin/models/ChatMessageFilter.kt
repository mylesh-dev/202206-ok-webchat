package net.murzikov.webchat.common.models

data class ChatMessageFilter(
    var searchString: String = "",
    var userId: ChatlUserId = ChatlUserId.NONE
)
