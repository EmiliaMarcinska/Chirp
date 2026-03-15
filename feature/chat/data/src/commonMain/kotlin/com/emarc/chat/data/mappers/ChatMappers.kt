package com.emarc.chat.data.mappers

import com.emarc.chat.data.dto.ChatDto
import com.emarc.chat.database.entities.ChatEntity
import com.emarc.chat.database.entities.ChatInfoEntity
import com.emarc.chat.database.entities.ChatWithParticipants
import com.emarc.chat.database.entities.MessageWithSender
import com.emarc.chat.domain.models.Chat
import com.emarc.chat.domain.models.ChatInfo
import com.emarc.chat.domain.models.ChatMessage
import com.emarc.chat.domain.models.ChatMessageDeliveryStatus
import com.emarc.chat.domain.models.ChatParticipant
import kotlin.time.Instant

typealias DataMessageWithSender = MessageWithSender
typealias DomainMessageWithSender = com.emarc.chat.domain.models.MessageWithSender

fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage? = null
): Chat {
    return Chat(
        id = chatId,
        participants = participants,
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = lastMessage
    )
}

fun ChatWithParticipants.toDomain(): Chat {
    return Chat(
        id = chat.chatId,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun Chat.toEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds()
    )
}

fun DataMessageWithSender.toDomain(): DomainMessageWithSender {
    return DomainMessageWithSender(
        message = message.toDomain(),
        sender = sender.toDomain(),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.message.deliveryStatus)
    )
}

fun ChatInfoEntity.toDomain(): ChatInfo {
    return ChatInfo(
        chat = chat.toDomain(
            participants = this.participants.map { it.toDomain() }
        ),
        messages = messagesWithSenders.map { it.toDomain() }
    )
}