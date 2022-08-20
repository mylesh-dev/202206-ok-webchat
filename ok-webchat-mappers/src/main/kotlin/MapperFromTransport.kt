package net.murzikov.webchat.mappers

import kotlinx.datetime.Instant
import net.murzikov.webchat.api.models.*
import net.murzikov.webchat.common.ChatContext
import net.murzikov.webchat.common.NONE
import net.murzikov.webchat.common.models.*
import net.murzikov.webchat.common.stubs.ChatStubs
import net.murzikov.webchat.mappers.exceptions.UnknownRequestClass

fun ChatContext.fromTransport(request: IRequest) = when(request){
    is MessageCreateRequest -> fromTransport(request)
    is MessageReadRequest   -> fromTransport(request)
    is MessageUpdateRequest -> fromTransport(request)
    is MessageDeleteRequest -> fromTransport(request)
    is MessageSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toMessageId() = this?.let { ChatMessageId(it) } ?: ChatMessageId.NONE
private fun String?.toUserId() = this?.let { ChatlUserId(it) } ?: ChatlUserId.NONE
private fun String?.toMessageWithId() = ChatMessage(id = this.toMessageId())
private fun IRequest?.requestId() = this?.requestId?.let { ChatRequestId(it) } ?: ChatRequestId.NONE

private fun MessageDebug?.transportToWorkMode(): ChatWorkMode = when(this?.mode) {
    MessageRequestDebugMode.PROD -> ChatWorkMode.PROD
    MessageRequestDebugMode.TEST -> ChatWorkMode.TEST
    MessageRequestDebugMode.STUB -> ChatWorkMode.STUB
    null -> ChatWorkMode.PROD
}

private fun MessageDebug?.transportToStubCase(): ChatStubs = when(this?.stub) {
    MessageRequestDebugStubs.SUCCESS -> ChatStubs.SUCCESS
    MessageRequestDebugStubs.NOT_FOUND -> ChatStubs.NOT_FOUND
    MessageRequestDebugStubs.BAD_ID -> ChatStubs.BAD_ID
    MessageRequestDebugStubs.CANNOT_UPDATE -> ChatStubs.CANNOT_UPDATE
    MessageRequestDebugStubs.CANNOT_DELETE -> ChatStubs.CANNOT_DELETE
    null -> ChatStubs.NONE
}

fun ChatContext.fromTransport(request: MessageCreateRequest) {
    command = ChatCommand.CREATE
    requestId = request.requestId()
    messageRequest = request.message?.toInternal() ?: ChatMessage()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ChatContext.fromTransport(request: MessageReadRequest) {
    command = ChatCommand.READ
    requestId = request.requestId()
    messageRequest = request.message?.id.toMessageWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ChatContext.fromTransport(request: MessageUpdateRequest) {
    command = ChatCommand.UPDATE
    requestId = request.requestId()
    messageRequest = request.message?.toInternal() ?: ChatMessage()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ChatContext.fromTransport(request: MessageDeleteRequest) {
    command = ChatCommand.DELETE
    requestId = request.requestId()
    messageRequest = request.message?.id.toMessageWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ChatContext.fromTransport(request: MessageSearchRequest) {
    command = ChatCommand.SEARCH
    requestId = request.requestId()
    messageFilterRequest = request.messageFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MessageSearchFilter?.toInternal(): ChatMessageFilter = ChatMessageFilter(
    searchString = this?.searchString ?: ""
)

private fun MessageCreateObject.toInternal(): ChatMessage {
    val timestampString = this.timestamp ?: Instant.NONE.toString()
    return ChatMessage(
        userId = this.userId.toUserId(),
        timestamp = Instant.parse(timestampString),
        content = this.content ?: "",
    )
}

private fun MessageUpdateObject.toInternal(): ChatMessage {
    val timestampString = this.timestamp ?: Instant.NONE.toString()
    return ChatMessage(
        id = this.id.toMessageId(),
        userId = this.userId.toUserId(),
        timestamp = Instant.parse(timestampString),
        content = this.content ?: "",
    )
}
