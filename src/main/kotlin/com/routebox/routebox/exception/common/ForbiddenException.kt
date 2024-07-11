package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class ForbiddenException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.FORBIDDEN,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.FORBIDDEN,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.FORBIDDEN,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.FORBIDDEN,
        exceptionType,
        optionalMessage,
        cause,
    )
}
