package com.emarc.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emarc.chat.database.dao.ChatDao
import com.emarc.chat.database.dao.ChatMessageDao
import com.emarc.chat.database.dao.ChatParticipantDao
import com.emarc.chat.database.dao.ChatParticipantsCrossRefDao
import com.emarc.chat.database.entities.ChatEntity
import com.emarc.chat.database.entities.ChatMessageEntity
import com.emarc.chat.database.entities.ChatParticipantCrossRef
import com.emarc.chat.database.entities.ChatParticipantEntity
import com.emarc.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatMessageEntity::class,
        ChatParticipantEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [
        LastMessageView::class
    ],
    version = 1
)
abstract class ChirpChatDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatParticipantsCrossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}