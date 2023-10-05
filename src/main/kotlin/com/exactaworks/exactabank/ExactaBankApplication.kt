package com.exactaworks.exactabank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExactaBankApplication

fun main(args: Array<String>) {
	runApplication<ExactaBankApplication>(*args)
}
