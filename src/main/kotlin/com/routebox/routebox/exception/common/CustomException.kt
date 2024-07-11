package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class CustomException : RuntimeException {
    val httpStatus: HttpStatus
    val code: Int
    final override val message: String
    final override val cause: Throwable?

    constructor(httpStatus: HttpStatus, exceptionType: CustomExceptionType) {
        this.httpStatus = httpStatus
        this.code = exceptionType.code
        this.message = exceptionType.message
        this.cause = null
    }

    constructor(httpStatus: HttpStatus, exceptionType: CustomExceptionType, optionalMessage: String) {
        this.httpStatus = httpStatus
        this.code = exceptionType.code
        this.message = exceptionType.message + " " + optionalMessage
        this.cause = null
    }

    constructor(httpStatus: HttpStatus, exceptionType: CustomExceptionType, cause: Throwable?) {
        this.httpStatus = httpStatus
        this.code = exceptionType.code
        this.message = exceptionType.message
        this.cause = cause
    }

    constructor(
        httpStatus: HttpStatus,
        exceptionType: CustomExceptionType,
        optionalMessage: String,
        cause: Throwable?,
    ) {
        this.httpStatus = httpStatus
        this.code = exceptionType.code
        this.message = exceptionType.message + " " + optionalMessage
        this.cause = cause
    }
}
