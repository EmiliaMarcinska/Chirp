package com.emarc.chat.data.message

import com.emarc.chat.database.ChirpChatDatabase
import com.emarc.chat.domain.message.MessageRepository
import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.core.data.database.safeDatabaseUpdate
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase
): MessageRepository {

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
}