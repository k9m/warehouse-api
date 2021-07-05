package org.k9m.warehouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class WarehouseApiApplication

fun main(args: Array<String>) {
	runApplication<WarehouseApiApplication>(*args)
}
