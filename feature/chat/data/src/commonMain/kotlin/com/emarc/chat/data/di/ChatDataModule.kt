package com.emarc.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.emarc.chat.data.chat.KtorChatParticipantService
import com.emarc.chat.data.chat.KtorChatService
import com.emarc.chat.data.chat.OfflineFirstChatRepository
<<<<<<< Updated upstream
=======
import com.emarc.chat.data.chat.WebSocketChatConnectionClient
import com.emarc.chat.data.message.KtorChatMessageService
import com.emarc.chat.data.message.OfflineFirstMessageRepository
import com.emarc.chat.data.network.ConnectionRetryHandler
import com.emarc.chat.data.network.KtorWebSocketConnector
>>>>>>> Stashed changes
import com.emarc.chat.database.DatabaseFactory
import com.emarc.chat.domain.chat.ChatParticipantService
import com.emarc.chat.domain.chat.ChatRepository
import com.emarc.chat.domain.chat.ChatService
<<<<<<< Updated upstream
=======
import com.emarc.chat.domain.message.ChatMessageService
import com.emarc.chat.domain.message.MessageRepository
import kotlinx.serialization.json.Json
>>>>>>> Stashed changes
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
<<<<<<< Updated upstream
=======
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    singleOf(::ConnectionRetryHandler)
    singleOf(::KtorWebSocketConnector)
    singleOf(::KtorChatMessageService) bind ChatMessageService::class
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
>>>>>>> Stashed changes
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}