package net.murzikov.webchat.api

import net.murzikov.webchat.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SerializationTest {
    private val request = MessageCreateRequest(
        requestId = "000",
        debug = MessageDebug(
            mode = MessageRequestDebugMode.STUB,
            stub = MessageRequestDebugStubs.NOT_FOUND
        ),
        message = MessageCreateObject(
            userId = "012",
            timestamp = "2022-08-13T10:20:30Z",
            content = "message content"
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)

        assertContains(json, Regex("\"userId\":\\s*\"012\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"notFound\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as MessageCreateRequest

        assertEquals(request, obj)
    }
}
