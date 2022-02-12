package com.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class OnlineStoreApplication

fun main(args: Array<String>) {
	runApplication<OnlineStoreApplication>(*args)
}
