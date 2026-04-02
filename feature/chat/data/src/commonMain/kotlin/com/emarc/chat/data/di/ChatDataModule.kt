package com.emarc.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.emarc.chat.data.chat.KtorChatParticipantService
import com.emarc.chat.data.chat.KtorChatService
import com.emarc.chat.data.chat.OfflineFirstChatRepository
import com.emarc.chat.data.chat.WebSocketChatConnectionClient
import com.emarc.chat.data.message.KtorChatMessageService
import com.emarc.chat.data.message.OfflineFirstMessageRepository
import com.emarc.chat.data.network.ConnectionRetryHandler
import com.emarc.chat.data.network.KtorWebSocketConnector
import com.emarc.chat.database.DatabaseFactory
import com.emarc.chat.domain.chat.ChatConnectionClient
import com.emarc.chat.domain.chat.ChatParticipantService
import com.emarc.chat.domain.chat.ChatRepository
import com.emarc.chat.domain.chat.ChatService
import com.emarc.chat.domain.message.MessageRepository
import kotlinx.serialization.json.Json
import com.emarc.chat.domain.message.ChatMessageService
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
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}