package com.shakirfattani.employee_management.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import com.shakirfattani.employee_management.models.Employee
import com.shakirfattani.employee_management.models.TimeOffRequest
import com.shakirfattani.employee_management.repositories.EmployeeRepository
import com.shakirfattani.employee_management.repositories.TimeOffRequestRepository
import com.shakirfattani.employee_management.utils.DateUtils
import java.time.ZoneId

@Service
@Transactional
class EmployeeService(private val employeeRepository: EmployeeRepository,
                      private val requestTypeService: TimeOffRequestTypeService,
                      private val timeOffRequestRepository: TimeOffRequestRepository) {

  fun getAllEmployees(timezone: String): List<Employee> {
    val zoneId = ZoneId.of(timezone)
    return employeeRepository.findAll().map {
      it.createdAt = DateUtils.convertToLocalTime(it.createdAt!!, zoneId)
      it.modifiedAt = DateUtils.convertToLocalTime(it.modifiedAt!!, zoneId)
      it
    }.toMutableList()
  }

  fun getEmployeeById(id: UUID): Employee?  {
    return employeeRepository.findById(id).orElse(null)?.let {
      it.modifiedAt = DateUtils.convertToLocalTime(it.modifiedAt!!, ZoneId.of("UTC"))
      it.createdAt = DateUtils.convertToLocalTime(it.createdAt!!, ZoneId.of("UTC"))
      it
    }
  }

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

  fun requestTimeOff(timeOffRequest: TimeOffRequest): TimeOffRequest {

    return timeOffRequest
  }
}
