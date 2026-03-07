package com.emarc.chirp

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}