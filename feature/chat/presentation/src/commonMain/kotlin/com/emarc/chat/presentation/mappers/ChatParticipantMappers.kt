package com.emarc.chat.presentation.mappers

import com.emarc.chat.domain.models.ChatParticipant
import com.emarc.core.designsystem.components.avatar.ChatParticipantUi
import com.emarc.core.domain.auth.User

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}

fun User.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    )
}