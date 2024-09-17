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
import java.time.ZoneOffset

@Service
@Transactional
class EmployeeService(
  private val employeeRepository: EmployeeRepository,
  private val requestTypeService: TimeOffRequestTypeService,
  private val timeOffRequestRepository: TimeOffRequestRepository,

) {

  fun getAllEmployees(timezone: String): List<Employee> {
    val zoneId = ZoneId.of(timezone)
    return employeeRepository.findAll().map {
      it.createdAt = DateUtils.convertUTCToLocalTime(it.createdAt!!, zoneId)
      it.modifiedAt = DateUtils.convertUTCToLocalTime(it.modifiedAt!!, zoneId)
      it
    }.toMutableList()
  }

  fun getEmployeeById(id: UUID, timezone: String): Employee?  {
    val zoneId = ZoneId.of(timezone)
    return employeeRepository.findById(id).orElse(null)?.let {
      it.modifiedAt = DateUtils.convertUTCToLocalTime(it.modifiedAt!!, zoneId)
      it.createdAt = DateUtils.convertUTCToLocalTime(it.createdAt!!, zoneId)
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

  fun getAllOffRequests(employeeId: UUID, timezone: String): List<TimeOffRequest> {
    val zoneId = ZoneId.of(timezone)
    getEmployeeById(employeeId, timezone) ?: throw Exception("employee not found")

    return timeOffRequestRepository.findAllByEmployeeId(employeeId = employeeId).map{
      it.copy(
        startDate = DateUtils.convertUTCToLocalTime(it.startDate, zoneId),
        endDate = DateUtils.convertUTCToLocalTime(it.endDate, zoneId),
        createdAt = DateUtils.convertUTCToLocalTime(it.createdAt!!, zoneId),
        modifiedAt = DateUtils.convertUTCToLocalTime(it.modifiedAt!!, zoneId)
      )
    }.toMutableList()
  }

  fun requestTimeOff(timeOffRequest: TimeOffRequest, currentTimeZone: String): TimeOffRequest {

    val currentZoneId = ZoneId.of(currentTimeZone)
    getEmployeeById(timeOffRequest.employeeId, currentTimeZone) ?: throw Exception("employee not found")
    requestTypeService.getTimeOffRequestTypeById(timeOffRequest.requestTypeId, currentTimeZone) ?: throw Exception("timeoff request type not found");

    val startDate = DateUtils.changeTimeZone(timeOffRequest.startDate, currentZoneId, ZoneOffset.UTC)
    val endDate = DateUtils.changeTimeZone(timeOffRequest.endDate, currentZoneId, ZoneOffset.UTC)

    if (startDate > endDate) {
      throw Exception("Ending Date can't be smaller then start date");
    }

    val timeOffRequests = timeOffRequestRepository.findAllByEmployeeId(timeOffRequest.employeeId)

    for (timeOffs in timeOffRequests) {
      if (timeOffs.startDate > endDate) continue // endDate is small then start date which is why we are ignore
      if (timeOffs.endDate < startDate) continue // startDate is bigger then end date which is why we are ignore

      if (timeOffs.endDate > startDate && timeOffs.startDate < startDate) throw Exception("start date is coming between one of OffTime Request")
      if (timeOffs.endDate > endDate && timeOffs.startDate < endDate) throw Exception("end date is coming between one of OffTime Request")

      if (timeOffs.startDate < startDate && timeOffs.endDate > endDate) throw Exception("this request is coming between one of OffTime Request")
      if (timeOffs.startDate > startDate && timeOffs.endDate < endDate) throw Exception("this time off request is cancelling other request")
    }

    // if this time request was cancelling any other time request then it will throw. otheriwse it will save this new time off request
    timeOffRequestRepository.save(timeOffRequest.copy(
      startDate = startDate,
      endDate = endDate,
    ))

    return timeOffRequest
  }
}
