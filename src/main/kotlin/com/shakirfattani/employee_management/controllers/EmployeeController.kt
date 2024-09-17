package com.shakirfattani.employee_management.controllers

import com.shakirfattani.employee_management.services.EmployeeService
import com.shakirfattani.employee_management.models.Employee
import com.shakirfattani.employee_management.models.TimeOffRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeService: EmployeeService) {
  @GetMapping
  fun getAllEmployees(@RequestParam timezone: String = "Asia/Dubai"): List<Employee> = employeeService.getAllEmployees(timezone)

  @GetMapping("/{id}")
  fun getEmployeeById(@PathVariable id: String): ResponseEntity<Employee> =
      employeeService.getEmployeeById(UUID.fromString(id))?.let { ResponseEntity.ok(it) }
          ?: ResponseEntity.notFound().build()

  @PostMapping
  fun createEmployee(@RequestBody employee: Employee): Employee = employeeService.createEmployee(employee)

  @PutMapping("/{id}")
  fun updateEmployee(@PathVariable id: String, @RequestBody updatedEmployee: Employee): ResponseEntity<Employee> =
      employeeService.updateEmployee(UUID.fromString(id), updatedEmployee)?.let { ResponseEntity.ok(it) }
          ?: ResponseEntity.notFound().build()

  @PostMapping
  fun requestTimeOff(@RequestBody timeOffRequest: TimeOffRequest): ResponseEntity<TimeOffRequest> {
      return try {
          val response = employeeService.requestTimeOff(timeOffRequest)
          ResponseEntity.ok(response)
      } catch (e: Exception) {
          ResponseEntity.badRequest().build()
      }
  }
}
