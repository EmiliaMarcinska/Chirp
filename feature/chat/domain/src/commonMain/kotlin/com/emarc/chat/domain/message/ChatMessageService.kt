package com.emarc.chat.domain.message

import com.emarc.chat.domain.models.ChatMessage
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult
import com.emarc.core.domain.util.Result

interface ChatMessageService {
    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError.Remote>

    suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote>
}