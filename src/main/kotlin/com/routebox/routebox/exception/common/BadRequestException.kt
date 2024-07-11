package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class BadRequestException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.BAD_REQUEST,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.BAD_REQUEST,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.BAD_REQUEST,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.BAD_REQUEST,
        exceptionType,
        optionalMessage,
        cause,
    )
}
