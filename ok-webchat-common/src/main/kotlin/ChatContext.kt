package net.murzikov.webchat.common

import kotlinx.datetime.Instant
import net.murzikov.webchat.common.models.*
import net.murzikov.webchat.common.stubs.ChatStubs

data class ChatContext(
    var command: ChatCommand = ChatCommand.NONE,
    var state: ChatState = ChatState.NONE,
    val errors: MutableList<ChatError> = mutableListOf(),

    var workMode: ChatWorkMode = ChatWorkMode.PROD,
    var stubCase: ChatStubs = ChatStubs.NONE,

    var requestId: ChatRequestId = ChatRequestId.NONE,
    var timeStarted: Instant = Instant.NONE,
    var messageRequest: ChatMessage = ChatMessage(),
    var messageFilterRequest: ChatMessageFilter = ChatMessageFilter(),
    var messageResponse: ChatMessage = ChatMessage(),
    var messagesResponse: MutableList<ChatMessage> = mutableListOf(),
)
