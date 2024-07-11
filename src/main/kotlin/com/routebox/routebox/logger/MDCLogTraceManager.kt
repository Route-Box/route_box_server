package com.routebox.routebox.logger

import org.slf4j.MDC
import java.util.UUID

object MDCLogTraceManager {

    private const val LOG_TRACE_ID_MDC_KEY: String = "LogTraceId"

    val logTraceId: String
        get() = MDC.get(LOG_TRACE_ID_MDC_KEY) ?: ""

    fun setLogTraceId() {
        MDC.put(LOG_TRACE_ID_MDC_KEY, UUID.randomUUID().toString().substring(0, 8))
    }

    fun removeLogTraceId() {
        MDC.remove(LOG_TRACE_ID_MDC_KEY)
    }
}
