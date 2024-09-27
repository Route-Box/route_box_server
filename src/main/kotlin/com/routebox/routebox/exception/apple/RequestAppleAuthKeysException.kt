package com.routebox.routebox.exception.apple

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.BadRequestException

class RequestAppleAuthKeysException(
    message: String?,
    cause: Throwable?,
) : BadRequestException(
    exceptionType = CustomExceptionType.REQUEST_APPLE_AUTH_KEYS,
    optionalMessage = message,
    cause = cause,
)
