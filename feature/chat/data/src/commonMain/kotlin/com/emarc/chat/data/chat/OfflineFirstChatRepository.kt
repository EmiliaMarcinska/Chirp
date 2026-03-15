package com.emarc.chat.data.chat

import com.emarc.chat.data.mappers.toDomain
import com.emarc.chat.data.mappers.toEntity
import com.emarc.chat.data.mappers.toLastMessageView
import com.emarc.chat.database.ChirpChatDatabase
import com.emarc.chat.database.entities.ChatWithParticipants
import com.emarc.chat.domain.chat.ChatRepository
import com.emarc.chat.domain.chat.ChatService
import com.emarc.chat.domain.models.Chat
import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.Result
import com.emarc.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
): ChatRepository {

    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList.map { it.toDomain() }
            }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )
                }

                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    messageDao = db.chatMessageDao
                )
            }
    }
}