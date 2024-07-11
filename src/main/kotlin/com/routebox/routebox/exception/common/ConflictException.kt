package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class ConflictException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.CONFLICT,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.CONFLICT,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.CONFLICT,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.CONFLICT,
        exceptionType,
        optionalMessage,
        cause,
    )
}
