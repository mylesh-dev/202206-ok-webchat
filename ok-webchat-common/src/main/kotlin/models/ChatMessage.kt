package net.murzikov.webchat.common.models

import kotlinx.datetime.Instant

data class ChatMessage(
    var id: ChatMessageId = ChatMessageId.NONE,
    var timestamp: Instant = Instant.NONE,
    var content: String = "",
    var userId: ChatlUserId = ChatlUserId.NONE
)
