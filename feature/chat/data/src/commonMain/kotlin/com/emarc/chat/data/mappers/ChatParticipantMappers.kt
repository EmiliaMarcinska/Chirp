package com.emarc.chat.data.mappers

import com.emarc.chat.data.dto.ChatParticipantDto
import com.emarc.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}