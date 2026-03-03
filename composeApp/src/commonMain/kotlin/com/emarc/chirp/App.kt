package com.emarc.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.emarc.auth.presentation.register_success.RegisterSuccessRoot
import com.emarc.chirp.navigation.DeepLinkListener
import com.emarc.chirp.navigation.NavigationRoot
import com.emarc.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    DeepLinkListener(navController)

    ChirpTheme {
        NavigationRoot(navController)
    }
}