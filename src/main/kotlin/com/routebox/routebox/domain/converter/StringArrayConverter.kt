package com.routebox.routebox.domain.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class StringArrayConverter : AttributeConverter<Array<String>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Array<String>?): String? {
        return attribute?.let {
            try {
                objectMapper.writeValueAsString(it)
            } catch (e: Exception) {
                throw IllegalArgumentException("Error converting Array<String> to JSON", e)
            }
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Array<String>? {
        return dbData?.let {
            try {
                objectMapper.readValue(it)
            } catch (e: Exception) {
                throw IllegalArgumentException("Error converting JSON to Array<String>", e)
            }
        }
    }
}
