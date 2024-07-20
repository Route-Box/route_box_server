package com.routebox.routebox.exception.security

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.UnauthorizedException

class InvalidTokenException(
    optionalMessage: String,
    cause: Throwable? = null,
) : UnauthorizedException(
    exceptionType = CustomExceptionType.INVALID_TOKEN,
    optionalMessage = optionalMessage,
    cause = cause,
)
