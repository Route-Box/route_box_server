package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class UnprocessableEntityException : CustomException {
    constructor(exceptionType: CustomExceptionType) : super(
        HttpStatus.UNPROCESSABLE_ENTITY,
        exceptionType,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String) : super(
        HttpStatus.UNPROCESSABLE_ENTITY,
        exceptionType,
        optionalMessage,
    )

    constructor(exceptionType: CustomExceptionType, cause: Throwable) : super(
        HttpStatus.UNPROCESSABLE_ENTITY,
        exceptionType,
        cause,
    )

    constructor(exceptionType: CustomExceptionType, optionalMessage: String, cause: Throwable) : super(
        HttpStatus.UNPROCESSABLE_ENTITY,
        exceptionType,
        optionalMessage,
        cause,
    )
}
