package com.emarc.chat.data.mappers

import com.emarc.chat.data.dto.ChatMessageDto
import com.emarc.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId
    )
}