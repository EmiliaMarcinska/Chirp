package com.emarc.core.domain.auth

import com.emarc.core.domain.util.DataError
import com.emarc.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ) : EmptyResult<DataError.Remote>
}