package com.emarc.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform