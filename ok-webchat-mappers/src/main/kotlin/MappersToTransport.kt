package net.murzikov.webchat.mappers

import kotlinx.datetime.Instant
import net.murzikov.webchat.api.models.*
import net.murzikov.webchat.common.ChatContext
import net.murzikov.webchat.common.NONE
import net.murzikov.webchat.common.models.*
import net.murzikov.webchat.mappers.exceptions.UnknownChatCommand

fun ChatContext.toTransportMessage(): IResponse = when (val cmd = command) {
    ChatCommand.CREATE -> toTransportCreate()
    ChatCommand.READ -> toTransportRead()
    ChatCommand.UPDATE -> toTransportUpdate()
    ChatCommand.DELETE -> toTransportDelete()
    ChatCommand.SEARCH -> toTransportSearch()
    ChatCommand.NONE -> throw UnknownChatCommand(cmd)
}

fun ChatContext.toTransportCreate() = MessageCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ChatState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    message =  messageResponse.toTransportMessage()
)

fun ChatContext.toTransportRead() = MessageReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ChatState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    message =  messageResponse.toTransportMessage()
)

fun ChatContext.toTransportUpdate() = MessageUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ChatState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    message =  messageResponse.toTransportMessage()
)

fun ChatContext.toTransportDelete() = MessageDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ChatState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    message =  messageResponse.toTransportMessage()
)

fun ChatContext.toTransportSearch() = MessageSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ChatState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    messages = messagesResponse.toTransportMessage()
)

fun List<ChatMessage>.toTransportMessage(): List<MessageResponseObject>? = this
    .map { it.toTransportMessage() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ChatMessage.toTransportMessage(): MessageResponseObject = MessageResponseObject(
    id = id.takeIf { it != ChatMessageId.NONE }?.asString(),
    timestamp = timestamp.takeIf { it != Instant.NONE }.toString(),
    content = content.takeIf { it.isNotBlank() },
    userId = userId.takeIf { it != ChatlUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportMessage(),
)

private fun Set<ChatMessagePermissionClient>.toTransportMessage(): Set<MessagePermissions>? = this
    .map { it.toTransportMessage() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun ChatMessagePermissionClient.toTransportMessage() = when (this) {
    ChatMessagePermissionClient.READ -> MessagePermissions.READ
    ChatMessagePermissionClient.WRITE -> MessagePermissions.WRITE
    ChatMessagePermissionClient.UPDATE -> MessagePermissions.UPDATE
    ChatMessagePermissionClient.DELETE -> MessagePermissions.DELETE
}

private fun List<ChatError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportMessage() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ChatError.toTransportMessage() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
