package com.routebox.routebox.exception.comment

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.NotFoundException

class CommentNotFoundException : NotFoundException(CustomExceptionType.COMMENT_NOT_FOUND)
