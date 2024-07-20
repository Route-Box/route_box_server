package com.routebox.routebox.exception.kakao

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.BadRequestException

class RequestKakaoUserInfoException(
    errorMessageFromKakaoApi: String?,
    cause: Throwable? = null,
) : BadRequestException(
    exceptionType = CustomExceptionType.REQUEST_KAKAO_USER_INFO,
    optionalMessage = errorMessageFromKakaoApi,
    cause = cause,
)
