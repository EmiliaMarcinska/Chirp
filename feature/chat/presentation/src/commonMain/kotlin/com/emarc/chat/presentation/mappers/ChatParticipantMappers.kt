package com.emarc.chat.presentation.mappers

import com.emarc.chat.domain.models.ChatParticipant
import com.emarc.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}