package com.routebox.routebox.exception.comment

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ForbiddenException

class CommentDeleteForbiddenException : ForbiddenException(CustomExceptionType.COMMENT_DELETE_FORBIDDEN)
