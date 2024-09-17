package com.shakirfattani.employee_management.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import com.shakirfattani.employee_management.models.Employee
import com.shakirfattani.employee_management.repositories.EmployeeRepository

@Service
@Transactional
class EmployeeService(private val employeeRepository: EmployeeRepository) {

  fun getAllEmployees(): List<Employee> = employeeRepository.findAll()

  fun getEmployeeById(id: UUID): Employee? = employeeRepository.findById(id).orElse(null)

  fun createEmployee(employee: Employee): Employee = employeeRepository.save(employee)

  fun updateEmployee(id: UUID, updatedEmployee: Employee): Employee? {
    val employee = employeeRepository.findById(id).orElse(null) ?: return null
    return employeeRepository.save(
      employee.copy(
        name = updatedEmployee.name,
        email = updatedEmployee.email,
        position = updatedEmployee.position,
        salary = updatedEmployee.salary,
      )
    )
  }
}
