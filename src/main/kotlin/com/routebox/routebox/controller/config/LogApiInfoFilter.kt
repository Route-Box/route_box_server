package com.routebox.routebox.controller.config

import com.routebox.routebox.logger.Logger
import com.routebox.routebox.logger.MDCLogTraceManager
import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.Part
import org.springframework.http.MediaType
import org.springframework.util.ObjectUtils
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors

class LogApiInfoFilter : OncePerRequestFilter() {
    companion object {
        val LOG_BLACK_LIST = arrayOf(
            "/swagger",
            "/v3/api-docs",
            "/actuator",
        )

        private val VISIBLE_TYPES: Set<MediaType> = setOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.MULTIPART_FORM_DATA,
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        MDCLogTraceManager.setLogTraceIdIfAbsent()

        try {
            if (isAsyncDispatch(request)) {
                filterChain.doFilter(request, response)
            } else {
                val doLog = LOG_BLACK_LIST.none { request.requestURI.startsWith(it) }
                val responseWrapper = ResponseWrapper(response)
                try {
                    if (!isMultipartFormData(request.contentType)) {
                        val requestWrapper = RequestWrapper(request)
                        if (doLog) {
                            logRequest(requestWrapper)
                        }
                        filterChain.doFilter(requestWrapper, responseWrapper)
                    } else {
                        val payload = request.parts.map { part -> processPart(part) }
                        Logger.info(
                            "Request: [${request.method}] uri=${request.requestURI}, " +
                                "content-type=multipart/form-data, payload={${payload.joinToString(", ") { it }}}",
                        )
                        filterChain.doFilter(request, responseWrapper)
                    }
                } finally {
                    if (doLog) logResponse(responseWrapper)
                    responseWrapper.copyBodyToResponse()
                }
            }
        } finally {
            MDCLogTraceManager.removeLogTraceId()
        }
    }

    private fun logRequest(request: RequestWrapper) {
        var uri = request.requestURI
        val queryString = request.queryString
        if (queryString != null) {
            uri += "?$queryString"
        }

        val content = StreamUtils.copyToByteArray(request.inputStream)
        if (ObjectUtils.isEmpty(content)) {
            Logger.info("Request: [${request.method}] uri=$uri")
        } else {
            val payloadInfo = getPayloadInfo(request.contentType, content)
            Logger.info("Request: [$uri] uri=$uri, $payloadInfo")
        }
    }

    private fun logResponse(response: ContentCachingResponseWrapper) {
        Logger.info("Response: status=${response.status}")
    }

    private fun getPayloadInfo(contentType: String?, content: ByteArray): String {
        val mediaType: MediaType =
            if (!contentType.isNullOrBlank()) MediaType.valueOf(contentType) else MediaType.APPLICATION_JSON
        var payloadInfo = "content-type=$mediaType, payload="

        if (mediaType.isCompatibleWith(MediaType.TEXT_HTML)) {
            return "${payloadInfo}HTML Content"
        }

        if (!isMediaTypeVisible(mediaType)) {
            return "${payloadInfo}Binary Content"
        }

        if (content.size >= 10000) {
            return "${payloadInfo}too many data."
        }

        val contentString = String(content)
        payloadInfo += if (mediaType == MediaType.APPLICATION_JSON) {
            contentString.replace("\n *".toRegex(), "").replace(",".toRegex(), ", ")
        } else {
            contentString
        }

        return payloadInfo
    }

    private fun isMultipartFormData(contentType: String?): Boolean =
        contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)

    /**
     * @param part
     * @return 전달받은 `part`의 내용을 담은 문자열
     */
    private fun processPart(part: Part): String =
        if (part.contentType == null) {
            // 파일이 아닌, 텍스트 데이터인 경우
            val value = BufferedReader(InputStreamReader(part.inputStream))
                .lines()
                .collect(Collectors.joining(","))
            "${part.name}:$value"
        } else {
            // 파일 데이터인 경우
            val fileSize = String.format("%.2f", convertByteToKB(part.size))
            "${part.name}:${part.submittedFileName}(${fileSize}KB)"
        }

    private fun convertByteToKB(sizeInByte: Long): Double = sizeInByte / 1024.0

    private fun isMediaTypeVisible(mediaType: MediaType): Boolean =
        VISIBLE_TYPES.any { visibleType -> visibleType.includes(mediaType) }
}

class RequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedInputStream: ByteArray? by lazy {
        StreamUtils.copyToByteArray(request.inputStream)
    }

    override fun getInputStream(): ServletInputStream {
        val byteArray = cachedInputStream ?: return super.getInputStream()
        return object : ServletInputStream() {
            private val inputStream = ByteArrayInputStream(byteArray)

            override fun isFinished(): Boolean = try {
                inputStream.available() == 0
            } catch (e: IOException) {
                Logger.error("Raised IOException at LogApiInfoFilter.RequestWrapper.isFinished(). Error=$e")
                true // safe finish
            }

            override fun isReady(): Boolean = true

            override fun setReadListener(listener: ReadListener): Unit = throw UnsupportedOperationException()

            override fun read(): Int = inputStream.read()
        }
    }
}

class ResponseWrapper(response: HttpServletResponse) : ContentCachingResponseWrapper(response)
