package com.routebox.routebox.exception.user

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.NotFoundException

class UserNotFoundException : NotFoundException(CustomExceptionType.USER_NOT_FOUND)
