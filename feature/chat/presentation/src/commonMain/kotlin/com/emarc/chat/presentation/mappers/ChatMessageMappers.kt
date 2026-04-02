package com.emarc.chat.presentation.mappers

import com.emarc.chat.domain.models.MessageWithSender
import com.emarc.chat.presentation.model.MessageUi
import com.emarc.chat.presentation.util.DateUtils

fun MessageWithSender.toUi(
    localUserId: String,
): MessageUi {
    val isFromLocalUser = this.sender.userId == localUserId
    return if(isFromLocalUser) {
        MessageUi.LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
            isMenuOpen = false,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt)
        )
    } else {
        MessageUi.OtherUserMessage(
            id = message.id,
            content = message.content,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt),
            sender = sender.toUi()
        )
    }
}