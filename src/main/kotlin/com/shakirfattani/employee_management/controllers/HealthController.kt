package com.shakirfattani.employee_management.controllers

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

	@GetMapping("/health")
	fun health() = "success"

	@GetMapping("/error")
	fun error() = "something went wrong"
}
