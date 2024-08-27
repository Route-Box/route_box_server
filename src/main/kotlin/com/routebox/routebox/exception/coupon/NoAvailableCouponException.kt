package com.routebox.routebox.exception.coupon

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.BadRequestException

class NoAvailableCouponException : BadRequestException(CustomExceptionType.NO_AVAILABLE_COUPON)
