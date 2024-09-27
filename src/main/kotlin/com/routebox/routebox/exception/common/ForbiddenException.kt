package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class ForbiddenException(
    exceptionType: CustomExceptionType,
    optionalMessage: String? = null,
    cause: Throwable? = null,
) : CustomException(
    httpStatus = HttpStatus.FORBIDDEN,
    exceptionType = exceptionType,
    optionalMessage = optionalMessage,
    cause = cause,
)
