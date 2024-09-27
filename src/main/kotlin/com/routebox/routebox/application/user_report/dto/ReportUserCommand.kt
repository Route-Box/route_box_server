package com.routebox.routebox.application.user_report.dto

data class ReportUserCommand(
    val reporterId: Long,
    val reportedUserId: Long,
)
