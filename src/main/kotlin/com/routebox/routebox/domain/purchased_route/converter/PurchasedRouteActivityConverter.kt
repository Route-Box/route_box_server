package com.routebox.routebox.domain.purchased_route.converter

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.routebox.routebox.domain.purchased_route.PurchasedRouteActivity
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class PurchasedRouteActivityConverter : AttributeConverter<List<PurchasedRouteActivity>, String> {

    private val mapper = jacksonObjectMapper().registerModules(JavaTimeModule())

    override fun convertToDatabaseColumn(attribute: List<PurchasedRouteActivity>): String =
        mapper.writeValueAsString(attribute)

    override fun convertToEntityAttribute(dbData: String): List<PurchasedRouteActivity> =
        mapper.readValue<List<PurchasedRouteActivity>>(dbData)
}
