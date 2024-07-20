package com.routebox.routebox.exception.apple

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.UnauthorizedException

class InvalidAppleIdTokenException(
    cause: Throwable?,
) : UnauthorizedException(
    exceptionType = CustomExceptionType.INVALID_APPLE_ID_TOKEN,
    cause = cause,
)
