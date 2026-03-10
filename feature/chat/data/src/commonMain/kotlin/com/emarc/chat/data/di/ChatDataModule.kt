package com.emarc.chat.data.di

import com.emarc.chat.data.chat.KtorChatParticipantService
import com.emarc.chat.data.chat.KtorChatService
import com.emarc.chat.domain.chat.ChatParticipantService
import com.emarc.chat.domain.chat.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
}