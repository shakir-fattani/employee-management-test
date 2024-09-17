package com.shakirfattani.employee_management.controllers

import com.shakirfattani.employee_management.services.EmployeeService
import com.shakirfattani.employee_management.models.Employee
import com.shakirfattani.employee_management.models.TimeOffRequestType
import com.shakirfattani.employee_management.services.TimeOffRequestTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/time-off-request-types")
class TimeOffRequestTypeController(private val timeOffRequestTypeService: TimeOffRequestTypeService) {
  @GetMapping
  fun getAllTimeOffRequestType(@RequestParam(required = false) timezone: String = "Asia/Dubai"): List<TimeOffRequestType> =
      timeOffRequestTypeService.getAllTimeOffRequestTypes(timezone)

  @GetMapping("/{id}")
  fun getTimeOffRequestTypeById(@PathVariable id: String, @RequestParam(required = false) timezone: String = "Asia/Dubai"): ResponseEntity<TimeOffRequestType> =
      timeOffRequestTypeService.getTimeOffRequestTypeById(UUID.fromString(id), timezone)?.let { ResponseEntity.ok(it) }
          ?: ResponseEntity.notFound().build()

  @PostMapping
  fun createTimeOffRequestType(@RequestBody timeOffRequestType: TimeOffRequestType): TimeOffRequestType =
      timeOffRequestTypeService.createTimeOffRequestType(timeOffRequestType)

  @PutMapping("/{id}")
  fun updateTimeOffRequestType(@PathVariable id: String, @RequestBody updatedTimeOffRequestType: TimeOffRequestType): ResponseEntity<TimeOffRequestType> =
      timeOffRequestTypeService.updateTimeOffRequestType(UUID.fromString(id), updatedTimeOffRequestType)?.let { ResponseEntity.ok(it) }
          ?: ResponseEntity.notFound().build()
}
