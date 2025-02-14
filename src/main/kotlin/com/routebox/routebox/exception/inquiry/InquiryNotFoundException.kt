package com.routebox.routebox.exception.inquiry

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.NotFoundException

class InquiryNotFoundException : NotFoundException(CustomExceptionType.INQUIRY_NOT_FOUND)
