package com.emarc.chirp

import androidx.compose.runtime.Composable
import com.emarc.auth.presentation.register.RegisterRoot
import com.emarc.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot()
    }
}