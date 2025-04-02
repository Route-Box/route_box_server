package com.routebox.routebox.exception.user

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.BadRequestException

class InsufficientPointException : BadRequestException(exceptionType = CustomExceptionType.INSUFFICIENT_POINT)
