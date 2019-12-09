package me.kcybulski.fixit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FixitApplication

fun main(args: Array<String>) {
	runApplication<FixitApplication>(*args)
}
