package com.routebox.routebox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RouteBoxServerApplication

fun main(args: Array<String>) {
    runApplication<RouteBoxServerApplication>(*args)
}
