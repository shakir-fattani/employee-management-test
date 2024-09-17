package com.shakirfattani.employee_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class EmployeeManagementApplication

fun main(args: Array<String>) {
	runApplication<EmployeeManagementApplication>(*args)
}
