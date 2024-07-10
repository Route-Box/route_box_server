package com.routebox.routebox.logger

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LogTrace {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val traceIdHolder: ThreadLocal<TraceId> = ThreadLocal<TraceId>()

    companion object {
        private const val START_PREFIX = "-->"
        private const val COMPLETE_PREFIX = "<--"
        private const val EX_PREFIX = "<X-"

        private fun addSpace(prefix: String, level: Int): String {
            val sb = StringBuilder()
            for (i in 0 until level) {
                sb.append(if ((i == level - 1)) "|$prefix" else "|   ")
            }
            return sb.toString()
        }
    }

    fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId: TraceId = traceIdHolder.get()
        val startTimeMs = System.currentTimeMillis()
        logger.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        val traceId: TraceId? = traceIdHolder.get()
        if (traceId == null) {
            traceIdHolder.set(TraceId())
        } else {
            traceIdHolder.set(traceId.createNextId())
        }
    }

    fun end(status: TraceStatus) {
        complete(status, null)
    }

    fun exception(status: TraceStatus, e: Exception?) {
        complete(status, e)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs: Long = stopTimeMs - status.startTimeMillis
        val traceId: TraceId = status.traceId
        if (e == null) {
            logger.info(
                "[{}] {}{} time={}ms",
                traceId.id,
                addSpace(COMPLETE_PREFIX, traceId.level),
                status.message,
                resultTimeMs,
            )
        } else {
            logger.error(
                "[{}] {}{} time={}ms ex={}",
                traceId.id,
                addSpace(EX_PREFIX, traceId.level),
                status.message,
                resultTimeMs,
                e.toString(),
            )
        }
        releaseTraceId()
    }

    private fun releaseTraceId() {
        val traceId: TraceId = traceIdHolder.get()
        if (traceId.isFirstLevel) {
            traceIdHolder.remove()
        } else {
            traceIdHolder.set(traceId.createPrevId())
        }
    }
}
