package com.routebox.routebox.exception.common

import com.routebox.routebox.exception.CustomExceptionType
import org.springframework.http.HttpStatus

abstract class CustomException(
    val httpStatus: HttpStatus,
    val exceptionType: CustomExceptionType,
    val optionalMessage: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException() {
    val code: Int = exceptionType.code
    override val message: String get() = createErrorMessage(exceptionType.message, optionalMessage)

    private fun createErrorMessage(baseMessage: String, optionalMessage: String?): String =
        if (optionalMessage.isNullOrBlank()) {
            baseMessage
        } else {
            "$baseMessage $optionalMessage"
        }
}
