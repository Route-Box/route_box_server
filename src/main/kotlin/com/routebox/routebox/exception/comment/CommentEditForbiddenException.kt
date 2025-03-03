package com.routebox.routebox.exception.comment

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ForbiddenException

class CommentEditForbiddenException : ForbiddenException(CustomExceptionType.COMMENT_EDIT_FORBIDDEN)
