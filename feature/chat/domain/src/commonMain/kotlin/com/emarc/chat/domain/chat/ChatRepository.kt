package com.emarc.chat.domain.chat

import com.emarc.chat.domain.models.Chat
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}