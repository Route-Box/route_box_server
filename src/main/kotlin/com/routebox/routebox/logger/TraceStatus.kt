package com.routebox.routebox.logger

data class TraceStatus(
    val traceId: TraceId,
    val startTimeMillis: Long,
    val message: String,
)
