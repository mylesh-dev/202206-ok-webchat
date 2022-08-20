import org.junit.Test
import kotlinx.datetime.Instant
import net.murzikov.webchat.api.models.*
import net.murzikov.webchat.common.ChatContext
import net.murzikov.webchat.common.NONE
import net.murzikov.webchat.common.models.*
import net.murzikov.webchat.common.stubs.ChatStubs
import net.murzikov.webchat.mappers.fromTransport
import net.murzikov.webchat.mappers.toTransportMessage
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = MessageCreateRequest(
            requestId = "000",
            debug = MessageDebug(
                mode = MessageRequestDebugMode.STUB,
                stub = MessageRequestDebugStubs.SUCCESS,
            ),
            message = MessageCreateObject(
                userId = "123",
                content = "Test message.",
            ),
        )

        val context = ChatContext()
        context.fromTransport(req)

        assertEquals(ChatStubs.SUCCESS, context.stubCase)
        assertEquals(ChatWorkMode.STUB, context.workMode)
        assertEquals("Test message.", context.messageRequest.content)
        assertEquals("123", context.messageRequest.userId.asString())
        assertEquals(Instant.NONE, context.messageRequest.timestamp)
    }

    @Test
    fun toTransport() {
        val context = ChatContext(
            requestId = ChatRequestId("000"),
            command = ChatCommand.CREATE,
            messageResponse = ChatMessage(
                userId = ChatlUserId("123"),
                content = "ttttt",
            ),
            errors = mutableListOf(
                ChatError(
                    code = "err",
                    group = "request",
                    field = "content",
                    message = "not found",
                )
            ),
            state = ChatState.RUNNING,
        )

        val req = context.toTransportMessage() as MessageCreateResponse

        assertEquals("000", req.requestId)
        assertEquals("ttttt", req.message?.content)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("content", req.errors?.firstOrNull()?.field)
        assertEquals("not found", req.errors?.firstOrNull()?.message)
    }


    @Test
    fun searchToTransport() {
        val context = ChatContext(
            requestId = ChatRequestId("000"),
            command = ChatCommand.SEARCH,
            messagesResponse = mutableListOf(
                ChatMessage(
                    userId = ChatlUserId("123"),
                    content = "ttttt",
                )
            ),
            errors = mutableListOf(
                ChatError(
                    code = "err",
                    group = "request",
                    field = "content",
                    message = "not found",
                )
            ),
            state = ChatState.FAILING,
        )

        val req = context.toTransportMessage() as MessageSearchResponse

        assertEquals("000", req.requestId)
        assertEquals("ttttt", req.messages?.firstOrNull()?.content)
        assertEquals("error", req.result?.value)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("content", req.errors?.firstOrNull()?.field)
        assertEquals("not found", req.errors?.firstOrNull()?.message)
    }
}
