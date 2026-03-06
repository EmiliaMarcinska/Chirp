package com.emarc.core.data.mappers

import com.emarc.core.data.dto.AuthInfoSerializable
import com.emarc.core.data.dto.UserSerializable
import com.emarc.core.domain.auth.AuthInfo
import com.emarc.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun UserSerializable.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}