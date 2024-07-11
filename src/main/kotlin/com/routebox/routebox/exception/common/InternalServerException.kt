package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class InternalServerException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        exceptionType,
        optionalMessage,
        cause,
    )
}
