package com.routebox.routebox.exception.user

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ConflictException

class UserNicknameDuplicationException : ConflictException(CustomExceptionType.USER_NICKNAME_DUPLICATION)
