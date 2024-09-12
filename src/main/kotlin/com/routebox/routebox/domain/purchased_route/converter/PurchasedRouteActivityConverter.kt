package com.routebox.routebox.domain.purchased_route.converter

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class PurchasedRouteActivityConverter : AttributeConverter<List<PurchasedRoute.PurchasedRouteActivity>, String> {

    private val mapper = jacksonObjectMapper().registerModules(JavaTimeModule())

    override fun convertToDatabaseColumn(attribute: List<PurchasedRoute.PurchasedRouteActivity>): String =
        mapper.writeValueAsString(attribute)

    override fun convertToEntityAttribute(dbData: String): List<PurchasedRoute.PurchasedRouteActivity> =
        mapper.readValue<List<PurchasedRoute.PurchasedRouteActivity>>(dbData)
}
