package com.emarc.chat.presentation.create_chat

import com.emarc.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat): CreateChatEvent
}