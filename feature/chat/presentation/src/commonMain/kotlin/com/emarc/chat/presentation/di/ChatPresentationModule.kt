package com.emarc.chat.presentation.di

import com.emarc.chat.presentation.chat_detail.ChatDetailViewModel
import com.emarc.chat.presentation.chat_list.ChatListViewModel
import com.emarc.chat.presentation.chat_list_detail.ChatListDetailViewModel
import com.emarc.chat.presentation.create_chat.CreateChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ChatListDetailViewModel)
    viewModelOf(::CreateChatViewModel)
    viewModelOf(::ChatDetailViewModel)
}