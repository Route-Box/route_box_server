package com.routebox.routebox.controller.config

import com.routebox.routebox.logger.MDCLogTraceManager
import com.routebox.routebox.logger.Logger
import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.util.ObjectUtils
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.Arrays

class LogApiInfoFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (!StringUtils.hasText(MDCLogTraceManager.logTraceId)) {
            MDCLogTraceManager.setLogTraceId()
        }

        try {
            if (isAsyncDispatch(request)) {
                filterChain.doFilter(request, response)
            } else {
                val doLog = Arrays.stream(LOG_BLACK_LIST).noneMatch { s: String? ->
                    request.requestURI.contains(
                        s!!,
                    )
                }
                val responseWrapper = ResponseWrapper(response)
                try {
                    if (isMultipartFormData(request.contentType)) {
                        Logger.info(
                            String.format(
                                "Request: [%s] uri=%s, payload=multipart/form-data",
                                request.method,
                                request.requestURI,
                            ),
                        )
                        filterChain.doFilter(request, responseWrapper)
                    } else {
                        val requestWrapper = RequestWrapper(request)
                        if (doLog) logRequest(requestWrapper)
                        filterChain.doFilter(requestWrapper, responseWrapper)
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

    @Throws(IOException::class)
    private fun logRequest(request: RequestWrapper) {
        var uri = request.requestURI
        val queryString = request.queryString
        if (queryString != null) {
            uri += "?$queryString"
        }

        val content = StreamUtils.copyToByteArray(request.inputStream)
        if (ObjectUtils.isEmpty(content)) {
            Logger.info(java.lang.String.format("Request: [%s] uri=%s", request.method, uri))
        } else {
            val payloadInfo = getPayloadInfo(request.contentType, content)
            Logger.info(java.lang.String.format("Request: [%s] uri=%s, %s", request.method, uri, payloadInfo))
        }
    }

    private fun logResponse(response: ContentCachingResponseWrapper) {
        Logger.info(String.format("Response: status=%d", response.status))
    }

    private fun getPayloadInfo(contentType: String?, content: ByteArray): String {
        var type: String? = contentType
        var payloadInfo = "content-type=$type, payload="

        if (type == null) {
            type = MediaType.APPLICATION_JSON_VALUE
        }

        if (MediaType.valueOf(type) == MediaType.valueOf("text/html") ||
            MediaType.valueOf(
                type,
            ) == MediaType.valueOf("text/css")
        ) {
            return payloadInfo + "HTML/CSS Content"
        }
        if (!isVisible(MediaType.valueOf(type))) {
            return payloadInfo + "Binary Content"
        }

        if (content.size >= 10000) {
            return payloadInfo + "too many data."
        }

        val contentString = String(content)
        // TODO: 추가적인 content-type case에 대한 로그 출력도 고민할 필요 있음.
        payloadInfo += if (type == MediaType.APPLICATION_JSON_VALUE) {
            contentString.replace("\n *".toRegex(), "").replace(",".toRegex(), ", ")
        } else {
            contentString
        }

        return payloadInfo
    }

    private fun isMultipartFormData(contentType: String?): Boolean =
        contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)

    private fun isVisible(mediaType: MediaType): Boolean = VISIBLE_TYPES.stream()
        .anyMatch { visibleType: MediaType ->
            visibleType.includes(
                mediaType,
            )
        }

    companion object {
        private val LOG_BLACK_LIST = arrayOf(
            "/swagger",
            "/v3/api-docs",
            "/actuator",
        )

        private val VISIBLE_TYPES: List<MediaType> = listOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.MULTIPART_FORM_DATA,
        )
    }
}

class RequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedInputStream = StreamUtils.copyToByteArray(request.inputStream)

    override fun getInputStream(): ServletInputStream {
        return object : ServletInputStream() {
            private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedInputStream)

            override fun isFinished(): Boolean {
                try {
                    return cachedBodyInputStream.available() == 0
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return false
            }

            override fun isReady(): Boolean = true

            override fun setReadListener(listener: ReadListener): Unit = throw UnsupportedOperationException()

            @Throws(IOException::class)
            override fun read(): Int = cachedBodyInputStream.read()
        }
    }
}

class ResponseWrapper(response: HttpServletResponse?) : ContentCachingResponseWrapper(response!!)
