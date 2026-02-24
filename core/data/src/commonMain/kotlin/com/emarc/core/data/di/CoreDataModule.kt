package com.emarc.core.data.di

import com.emarc.core.data.auth.KtorAuthService
import com.emarc.core.data.logging.KermitLogger
import com.emarc.core.data.networking.HttpClientFactory
import com.emarc.core.domain.auth.AuthService
import com.emarc.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single < ChirpLogger> { KermitLogger }
    single {
        HttpClientFactory(
            get()
        ).create(get())
    }
    singleOf(::KtorAuthService) bind AuthService::class
}