package com.emarc.chat.domain.chat

import com.emarc.chat.domain.models.ChatParticipant
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query: String
    ): Result<ChatParticipant, DataError.Remote>
}