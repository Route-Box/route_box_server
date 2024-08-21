package com.routebox.routebox.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {

    /**
     * 기본 ISO_LOCAL_DATE_TIME 포맷 ("yyyy-MM-ddTHH:mm:ss")을 사용한 변환
     */
    fun parseToLocalDateTime(dateTimeString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return LocalDateTime.parse(dateTimeString, formatter)
    }
}
