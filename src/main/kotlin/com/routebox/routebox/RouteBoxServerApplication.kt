package com.routebox.routebox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@ConfigurationPropertiesScan
@PropertySource("classpath:/env.properties")
@SpringBootApplication
class RouteBoxServerApplication

fun main(args: Array<String>) {
    runApplication<RouteBoxServerApplication>(*args)
}
