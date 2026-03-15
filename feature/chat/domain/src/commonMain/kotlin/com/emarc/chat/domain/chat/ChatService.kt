package com.emarc.chat.domain.chat

import com.emarc.chat.domain.models.Chat
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result

interface ChatService {
    suspend fun createChat(
        otherUserIds: List<String>
    ): Result<Chat, DataError.Remote>

    suspend fun getChats(): Result<List<Chat>, DataError.Remote>

    suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote>
}