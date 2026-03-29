package com.emarc.chat.data.chat

import com.emarc.chat.data.dto.websocket.WebSocketMessageDto
import com.emarc.chat.data.mappers.toNewMessage
import com.emarc.chat.data.network.KtorWebSocketConnector
import com.emarc.chat.database.ChirpChatDatabase
import com.emarc.chat.domain.chat.ChatConnectionClient
import com.emarc.chat.domain.chat.ChatRepository
import com.emarc.chat.domain.error.ConnectionError
import com.emarc.chat.domain.message.MessageRepository
import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.chat.domain.models.ConnectionState
import com.emarc.core.domain.auth.SessionStorage
import com.emarc.core.domain.util.EmptyResult
import com.emarc.core.domain.util.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val webSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val database: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
): ChatConnectionClient {

    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")

    override val connectionState = webSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto = message.toNewMessage()
        val webSocketMessage = WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )
        val rawJsonPayload = json.encodeToString(webSocketMessage)

        return webSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { error ->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }
}