package com.routebox.routebox.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {

    /**
     * 기본 ISO_LOCAL_DATE_TIME 포맷 ("yyyy-MM-ddTHH:mm:ss")을 사용한 변환
     */
    fun parseToLocalDateTime(dateTimeString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    /**
     * DATE 포맷 ("yyyy-MM-dd")을 사용한 변환
     */
    fun parseToLocalDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        return LocalDate.parse(dateString, formatter)
    }

    /**
     * TIME 포맷 ("HH:mm:ss")을 사용한 변환
     */
    fun parseToLocalTime(timeString: String): LocalTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        return LocalTime.parse(timeString, formatter)
    }
}
