package com.routebox.routebox.domain.route_report.converter

import com.routebox.routebox.domain.route_report.constant.RouteReportReasonType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RouteReportReasonTypesConverter : AttributeConverter<List<RouteReportReasonType>, String> {
    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: List<RouteReportReasonType>?): String {
        if (attribute == null) {
            return ""
        }
        return attribute.joinToString(separator = DELIMITER)
    }

    override fun convertToEntityAttribute(dbData: String): List<RouteReportReasonType> {
        if (dbData.isBlank()) {
            return emptyList()
        }
        return dbData.split(DELIMITER)
            .map { RouteReportReasonType.valueOf(it) }
            .toList()
    }
}
