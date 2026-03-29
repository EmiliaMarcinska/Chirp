package com.emarc.chat.domain.chat

import com.emarc.chat.domain.error.ConnectionError
import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ConnectionState
import com.emarc.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
    suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError>
}