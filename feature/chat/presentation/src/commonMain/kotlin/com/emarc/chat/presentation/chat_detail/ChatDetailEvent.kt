package com.emarc.chat.presentation.chat_detail

import com.emarc.core.presentation.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft: ChatDetailEvent
    data class OnError(val error: UiText): ChatDetailEvent
    data object OnNewMessage: ChatDetailEvent
}