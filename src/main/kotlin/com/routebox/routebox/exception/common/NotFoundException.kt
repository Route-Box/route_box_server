package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class NotFoundException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.NOT_FOUND,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.NOT_FOUND,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.NOT_FOUND,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.NOT_FOUND,
        exceptionType,
        optionalMessage,
        cause,
    )
}
