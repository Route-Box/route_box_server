package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class UnauthorizedException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.UNAUTHORIZED,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.UNAUTHORIZED,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.UNAUTHORIZED,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.UNAUTHORIZED,
        exceptionType,
        optionalMessage,
        cause,
    )
}
