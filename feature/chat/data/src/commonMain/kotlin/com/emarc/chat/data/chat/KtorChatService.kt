package com.emarc.chat.data.chat

import com.emarc.chat.data.dto.ChatDto
import com.emarc.chat.data.dto.request.CreateChatRequest
import com.emarc.chat.data.mappers.toDomain
import com.emarc.chat.domain.chat.ChatService
import com.emarc.chat.domain.models.Chat
import com.emarc.core.data.networking.post
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result
import com.emarc.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(
    private val httpClient: HttpClient
): ChatService {

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chat",
            body = CreateChatRequest(
                otherUserIds = otherUserIds
            )
        ).map { it.toDomain() }
    }
}