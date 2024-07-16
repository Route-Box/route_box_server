package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class NotFoundException(
    exceptionType: CustomExceptionType,
    optionalMessage: String? = null,
    cause: Throwable? = null,
) : CustomException(
    httpStatus = HttpStatus.NOT_FOUND,
    exceptionType = exceptionType,
    optionalMessage = optionalMessage,
    cause = cause,
)
