package com.routebox.routebox.logger

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Component
@Aspect
class LogTraceAspect(
    private val logTrace: LogTrace,
) {
    @Around(
        "com.routebox.routebox.logger.Pointcuts.controllerPoint() || " +
            "com.routebox.routebox.logger.Pointcuts.servicePoint() || " +
            "com.routebox.routebox.logger.Pointcuts.infrastructurePoint()",
    )
    @Throws(Throwable::class)
    fun execute(joinPoint: ProceedingJoinPoint): Any {
        var status: TraceStatus? = null
        try {
            val message = joinPoint.signature.toShortString()
            status = logTrace.begin(message)

            // Logic call
            val result = joinPoint.proceed()

            logTrace.end(status)
            return result
        } catch (ex: Exception) {
            logTrace.exception((status)!!, ex)
            throw ex
        }
    }
}