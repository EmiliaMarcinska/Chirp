package com.emarc.chat.domain.message

import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>
}