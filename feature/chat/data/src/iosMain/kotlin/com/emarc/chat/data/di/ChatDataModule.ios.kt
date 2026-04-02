package com.emarc.chat.data.di

import com.emarc.chat.data.lifecycle.AppLifecycleObserver
import com.emarc.chat.data.network.ConnectionErrorHandler
import com.emarc.chat.data.network.ConnectivityObserver
import com.emarc.chat.database.DatabaseFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory() }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
    singleOf(::ConnectionErrorHandler)
}