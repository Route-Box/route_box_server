package com.routebox.routebox.exception

import com.routebox.routebox.exception.common.CustomException
import com.routebox.routebox.logger.Logger
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<ErrorResponse> {
        Logger.error("Custom Exception: ${ExceptionUtils.getExceptionStackTrace(ex)}")

        return ResponseEntity
            .status(ex.httpStatus)
            .body(ErrorResponse(ex.code, ex.message))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        Logger.error("Validation Exception: ${ExceptionUtils.getExceptionStackTrace(ex)}")

        val errorDetails = ex.bindingResult
            .fieldErrors
            .map { fieldError ->
                ValidationErrorDetailResponse(
                    fieldError.field,
                    fieldError.defaultMessage ?: "",
                )
            }

        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(
                ValidationErrorResponse(
                    GlobalExceptionType.METHOD_ARGUMENT_NOT_VALID.code,
                    GlobalExceptionType.METHOD_ARGUMENT_NOT_VALID.message,
                    errorDetails,
                ),
            )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ValidationErrorResponse> {
        Logger.error("Validation Exception: ${ExceptionUtils.getExceptionStackTrace(ex)}")

        val errorDetails = ex.constraintViolations.map { violation ->
            ValidationErrorDetailResponse(
                getFieldNameFromConstraintViolation(violation),
                getMessageFromConstraintViolation(violation),
            )
        }

        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(
                ValidationErrorResponse(
                    GlobalExceptionType.CONSTRAINT_VIOLATION.code,
                    GlobalExceptionType.CONSTRAINT_VIOLATION.message,
                    errorDetails,
                ),
            )
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        Logger.error("Spring MVC Basic Exception: ${ExceptionUtils.getExceptionStackTrace(ex)}")

        val exceptionType = GlobalExceptionType.from(ex::class.java) ?: GlobalExceptionType.UNHANDLED

        return ResponseEntity
            .status(statusCode)
            .body(
                ErrorResponse(
                    exceptionType.code,
                    "${exceptionType.message} ${ex.message}",
                ),
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        Logger.error("UnHandled Exception: ${ExceptionUtils.getExceptionStackTrace(ex)}")

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    GlobalExceptionType.UNHANDLED.code,
                    "${GlobalExceptionType.UNHANDLED.message} ${ex.message}",
                ),
            )
    }

    /**
     * Violation exception이 발생한 filed의 filed name을 return한다.
     *
     * @return Violation exception이 발생한 filed의 filed name
     */
    private fun getFieldNameFromConstraintViolation(violation: ConstraintViolation<*>): String {
        val propertyPath = violation.propertyPath.toString()
        val dotIdx = propertyPath.lastIndexOf(".")
        return propertyPath.substring(dotIdx + 1)
    }

    /**
     * 발생한 violation exception의 message(설명)을 return한다.
     *
     * @return 발생한 violation exception의 message(설명)
     */
    private fun getMessageFromConstraintViolation(violation: ConstraintViolation<*>): String = violation.message
}

enum class GlobalExceptionType(
    val code: Int,
    val message: String,
    val type: Class<out Exception>?,
) {
    UNHANDLED(1000, "알 수 없는 서버 에러가 발생했습니다.", null),

    /**
     * Validation Exception
     */
    METHOD_ARGUMENT_NOT_VALID(1200, "요청 데이터가 잘못되었습니다. 요청 데이터의 값 또는 형식이 잘못되었거나, 필수값이 누락되지는 않았는지 확인해주세요.", MethodArgumentNotValidException::class.java),
    CONSTRAINT_VIOLATION(1200, "요청 데이터가 잘못되었습니다. 요청 데이터의 값 또는 형식이 잘못되었거나, 필수값이 누락되지는 않았는지 확인해주세요.", ConstraintViolationException::class.java),

    /**
     * Spring MVC Default Exception
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(1300, "지원하지 않는 요청 방식입니다.", HttpRequestMethodNotSupportedException::class.java),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(1301, "지원하지 않는 요청 데이터 타입입니다.", HttpMediaTypeNotSupportedException::class.java),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(1302, "요청한 데이터 타입으로 응답을 만들어 낼 수 없습니다.", HttpMediaTypeNotAcceptableException::class.java),
    MISSING_PATH_VARIABLE(1303, "필요로 하는 path variable이 누락 되었습니다.", MissingPathVariableException::class.java),
    MISSING_SERVLET_REQUEST_PARAMETER(1304, "필요로 하는 request parameter가 누락 되었습니다.", MissingServletRequestParameterException::class.java),
    MISSING_REQUEST_HEADER(1305, "필요로 하는 request header가 누락 되었습니다.", MissingRequestHeaderException::class.java),
    SERVLET_REQUEST_BINDING(1306, "복구 불가능한 fatal binding exception이 발생했습니다.", ServletRequestBindingException::class.java),
    CONVERSION_NOT_SUPPORTED(1307, "Bean property에 대해 적절한 editor 또는 convertor를 찾을 수 없습니다.", ConversionNotSupportedException::class.java),
    TYPE_MISMATCH(1308, "Bean property를 설정하던 중 type mismatch로 인한 예외가 발생했습니다.", TypeMismatchException::class.java),
    HTTP_MESSAGE_NOT_READABLE(1309, "읽을 수 없는 요청입니다. 요청 정보가 잘못되지는 않았는지 확인해주세요.", HttpMessageNotReadableException::class.java),
    HTTP_MESSAGE_NOT_WRITABLE(1310, "응답 데이터를 생성할 수 없습니다.", HttpMessageNotWritableException::class.java),
    MISSING_SERVLET_REQUEST_PART(1311, "multipart/form-data 형식의 요청 데이터에 대해 일부가 손실되거나 누락되었습니다.", MissingServletRequestPartException::class.java),
    NO_HANDLER_FOUND(1312, "알 수 없는 에러가 발생했으며, 에러를 처리할 handler를 찾지 못했습니다.", NoHandlerFoundException::class.java),
    ASYNC_REQUEST_TIMEOUT(1313, "요청에 대한 응답 시간이 초과되었습니다.", AsyncRequestTimeoutException::class.java),
    BIND(1314, "Request binding에 실패했습니다. 요청 데이터를 확인해주세요.", BindException::class.java),
    ;

    companion object {
        fun from(classType: Class<out Exception>): GlobalExceptionType? {
            val exceptionType = entries.firstOrNull { it.type?.isAssignableFrom(classType) == true }
            if (exceptionType == null) {
                Logger.error("정의되지 않은 exception이 발생하였습니다. Type of exception=$classType")
            }
            return exceptionType
        }
    }
}

data class ErrorResponse(
    val code: Int,
    val message: String,
)

data class ValidationErrorResponse(
    val code: Int,
    val message: String,
    val errors: List<ValidationErrorDetailResponse>,
)

data class ValidationErrorDetailResponse(
    val field: String,
    val message: String,
)
