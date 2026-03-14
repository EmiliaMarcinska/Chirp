package com.emarc.chat.data.di

import com.emarc.chat.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
}