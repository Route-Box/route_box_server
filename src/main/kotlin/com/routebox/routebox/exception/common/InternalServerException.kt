package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class InternalServerException(
    exceptionType: CustomExceptionType,
    optionalMessage: String? = null,
    cause: Throwable? = null,
) : CustomException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    exceptionType = exceptionType,
    optionalMessage = optionalMessage,
    cause = cause,
)
