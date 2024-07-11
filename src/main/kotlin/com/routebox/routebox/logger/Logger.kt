package com.routebox.routebox.logger

import org.slf4j.LoggerFactory

/**
 * Log trace id와 함께 로그를 남길 때 사용하기 위한 logger class.
 * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
 */
object Logger {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 전달받은 내용을 log trace id와 함께 trace level로 로깅한다.
     * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    fun trace(content: String?) {
        logger.trace("[{}] {}", MDCLogTraceManager.logTraceId, content)
    }

    /**
     * 전달받은 내용을 log trace id와 함께 debug level로 로깅한다.
     * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    fun debug(content: String?) {
        logger.debug("[{}] {}", MDCLogTraceManager.logTraceId, content)
    }

    /**
     * 전달받은 내용을 log trace id와 함께 info level로 로깅한다.
     * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    fun info(content: String?) {
        logger.info("[{}] {}", MDCLogTraceManager.logTraceId, content)
    }

    /**
     * 전달받은 내용을 log trace id와 함께 warn level로 로깅한다.
     * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    fun warn(content: String?) {
        logger.warn("[{}] {}", MDCLogTraceManager.logTraceId, content)
    }

    /**
     * 전달받은 내용을 log trace id와 함께 error level로 로깅한다.
     * Log trace id란 각 API 요청에 대해 고유하게 할당된 식별자를 의미한다.
     *
     * @param content 로그에 출력할 내용
     */
    fun error(content: String?) {
        logger.error("[{}] {}", MDCLogTraceManager.logTraceId, content)
    }
}
