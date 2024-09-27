package com.routebox.routebox.domain.purchased_route.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class PurchasedRouteStylesConverter : AttributeConverter<List<String>, String> {
    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: List<String>): String =
        attribute.joinToString(separator = DELIMITER)

    override fun convertToEntityAttribute(dbData: String): List<String> {
        if (dbData.isBlank()) {
            return listOf()
        }
        return dbData.split(DELIMITER).toList()
    }
}
