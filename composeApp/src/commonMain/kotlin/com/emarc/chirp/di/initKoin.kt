package com.emarc.chirp.di

import com.emarc.auth.presentation.di.authPresentationModule
import com.emarc.chat.data.di.chatDataModule
import com.emarc.chat.presentation.di.chatPresentationModule
import com.emarc.core.data.di.coreDataModule
import com.emarc.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            corePresentationModule,
            chatPresentationModule,
            chatDataModule
        )
    }
}