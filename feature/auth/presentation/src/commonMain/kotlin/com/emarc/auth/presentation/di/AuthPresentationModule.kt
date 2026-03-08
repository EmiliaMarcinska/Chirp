package com.emarc.auth.presentation.di

import com.emarc.auth.presentation.email_verification.EmailVerificationViewModel
import com.emarc.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.emarc.auth.presentation.login.LoginViewModel
import com.emarc.auth.presentation.register.RegisterViewModel
import com.emarc.auth.presentation.register_success.RegisterSuccessViewModel
import com.emarc.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}