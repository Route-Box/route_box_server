package com.routebox.routebox.exception.user

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ConflictException
class UserWithdrawnException : ConflictException(CustomExceptionType.USER_WITHDRAWN)
