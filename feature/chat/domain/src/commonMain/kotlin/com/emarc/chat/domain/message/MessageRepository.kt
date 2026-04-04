package com.emarc.chat.domain.message

import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.chat.domain.models.MessageWithSender
import com.emarc.chat.domain.models.OutgoingNewMessage
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult
import com.emarc.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError>

    suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError>

    suspend fun retryMessage(messageId: String): EmptyResult<DataError>

    fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>>
    suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote>
}