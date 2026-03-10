package com.emarc.chat.presentation.model

import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ChatParticipant
import com.emarc.core.designsystem.components.avatar.ChatParticipantUi
import kotlin.time.Instant

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String?
)