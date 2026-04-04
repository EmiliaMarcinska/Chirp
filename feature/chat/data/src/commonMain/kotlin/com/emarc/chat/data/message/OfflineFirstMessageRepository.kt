package com.emarc.chat.data.message

import com.emarc.chat.data.dto.websocket.OutgoingWebSocketDto
import com.emarc.chat.data.dto.websocket.WebSocketMessageDto
import com.emarc.chat.data.mappers.toDomain
import com.emarc.chat.data.mappers.toEntity
import com.emarc.chat.data.mappers.toWebSocketDto
import com.emarc.chat.data.network.KtorWebSocketConnector
import com.emarc.chat.database.ChirpChatDatabase
import com.emarc.chat.domain.message.ChatMessageService
import com.emarc.chat.domain.message.MessageRepository
import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.chat.domain.models.MessageWithSender
import com.emarc.chat.domain.models.OutgoingNewMessage
import com.emarc.core.data.database.safeDatabaseUpdate
import com.emarc.core.domain.auth.SessionStorage
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result
import com.emarc.core.domain.util.EmptyResult
import com.emarc.core.domain.util.onFailure
import com.emarc.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.onFailure
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val webSocketConnector: KtorWebSocketConnector,
    private val applicationScope: CoroutineScope
): MessageRepository {

    override suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            val dto = message.toWebSocketDto()

            val localUser = sessionStorage.observeAuthInfo().first()?.user
                ?: return Result.Failure(DataError.Local.NOT_FOUND)

            val entity = dto.toEntity(
                senderId = localUser.id,
                deliveryStatus = ChatMessageDeliveryStatus.SENDING
            )
            database.chatMessageDao.upsertMessage(entity)

            return webSocketConnector
                .sendMessage(dto.toJsonPayload())
                .onFailure { error ->
                    applicationScope.launch {
                        database.chatMessageDao.updateDeliveryStatus(
                            messageId = entity.messageId,
                            timestamp = Clock.System.now().toEpochMilliseconds(),
                            status = ChatMessageDeliveryStatus.FAILED.name
                        )
                    }.join()
                }
        }
    }

    override suspend fun retryMessage(messageId: String): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            println("Message ID retry $messageId")
            val message = database.chatMessageDao.getMessageById(messageId)
                ?: return Result.Failure(DataError.Local.NOT_FOUND)

            database.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                timestamp = Clock.System.now().toEpochMilliseconds(),
                status = ChatMessageDeliveryStatus.SENDING.name
            )

            val outgoingNewMessage = OutgoingWebSocketDto.NewMessage(
                chatId = message.chatId,
                messageId = messageId,
                content = message.content
            )
            return webSocketConnector
                .sendMessage(outgoingNewMessage.toJsonPayload())
                .onFailure {
                    applicationScope.launch {
                        database.chatMessageDao.upsertMessage(
                            message.copy(
                                deliveryStatus = ChatMessageDeliveryStatus.FAILED.name,
                                timestamp = Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    }.join()
                }
        }
    }

    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            database.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        return chatMessageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                return safeDatabaseUpdate {
                    database.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null // Only sync for most recent page
                    )
                    messages
                }
            }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return database
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { it.toDomain() }
            }
    }

    override suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote> {
        return chatMessageService
            .deleteMessage(messageId)
            .onSuccess {
                applicationScope.launch {
                    database.chatMessageDao.deleteMessageById(messageId)
                }.join()
            }
    }

    private fun OutgoingWebSocketDto.NewMessage.toJsonPayload(): String {
        val webSocketMessage = WebSocketMessageDto(
            type = type.name,
            payload = json.encodeToString(this)
        )
        return json.encodeToString(webSocketMessage)
    }
}