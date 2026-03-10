package com.emarc.chat.data.chat

import com.emarc.chat.data.dto.ChatParticipantDto
import com.emarc.chat.data.mappers.toDomain
import com.emarc.chat.domain.chat.ChatParticipantService
import com.emarc.chat.domain.models.ChatParticipant
import com.emarc.core.data.networking.get
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result
import com.emarc.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    private val httpClient: HttpClient
): ChatParticipantService {

    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { it.toDomain() }
    }
}