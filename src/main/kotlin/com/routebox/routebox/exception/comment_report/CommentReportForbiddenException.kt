package com.routebox.routebox.exception.comment_report

import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.common.ForbiddenException

class CommentReportForbiddenException : ForbiddenException(CustomExceptionType.COMMENT_REPORT_FORBIDDEN)
