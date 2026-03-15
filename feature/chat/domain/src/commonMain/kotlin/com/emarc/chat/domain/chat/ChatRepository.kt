package com.emarc.chat.domain.chat

import com.emarc.chat.domain.models.Chat
import com.emarc.chat.domain.models.ChatInfo
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult
import com.emarc.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getChatInfoById(chatId: String): Flow<ChatInfo>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
    suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote>
}