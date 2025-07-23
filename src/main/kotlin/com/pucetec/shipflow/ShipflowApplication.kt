package com.pucetec.shipflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShipflowApplication

fun main(args: Array<String>) {
    runApplication<ShipflowApplication>(*args)
}
