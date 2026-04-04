package com.emarc.core.domain.util

class DataErrorException(
    val error: DataError
): Exception()