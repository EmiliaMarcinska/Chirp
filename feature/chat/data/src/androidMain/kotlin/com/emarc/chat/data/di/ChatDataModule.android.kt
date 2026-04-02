package com.emarc.chat.data.di

<<<<<<< Updated upstream
=======
import com.emarc.chat.data.lifecycle.AppLifecycleObserver
import com.emarc.chat.data.network.ConnectionErrorHandler
import com.emarc.chat.data.network.ConnectivityObserver
>>>>>>> Stashed changes
import com.emarc.chat.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
<<<<<<< Updated upstream
=======
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
    singleOf(::ConnectionErrorHandler)
>>>>>>> Stashed changes
}