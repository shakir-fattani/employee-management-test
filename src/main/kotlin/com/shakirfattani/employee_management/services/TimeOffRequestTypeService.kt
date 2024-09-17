package com.shakirfattani.employee_management.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import com.shakirfattani.employee_management.models.TimeOffRequestType
import com.shakirfattani.employee_management.repositories.TimeOffRequestTypeRepository
import com.shakirfattani.employee_management.utils.DateUtils
import java.time.ZoneId

@Service
@Transactional
class TimeOffRequestTypeService(private val timeOffRequestTypeRepository: TimeOffRequestTypeRepository) {

  fun getAllTimeOffRequestTypes(timezone: String): List<TimeOffRequestType> {
    val zoneId = ZoneId.of(timezone)
    return timeOffRequestTypeRepository.findAll().map {
      it.createdAt = DateUtils.convertToLocalTime(it.createdAt!!, zoneId)
      it.modifiedAt = DateUtils.convertToLocalTime(it.modifiedAt!!, zoneId)
      it
    }.toMutableList();
  }

  fun getTimeOffRequestTypeById(id: UUID): TimeOffRequestType?  {
    val timeOffRequestType = timeOffRequestTypeRepository.findById(id).orElse(null)
    timeOffRequestType?.createdAt = DateUtils.convertToLocalTime(timeOffRequestType.createdAt!!, ZoneId.of("UTC"))
    timeOffRequestType?.modifiedAt = DateUtils.convertToLocalTime(timeOffRequestType.modifiedAt!!, ZoneId.of("UTC"))
    return timeOffRequestType
  }

  fun createTimeOffRequestType(timeOffRequestType: TimeOffRequestType): TimeOffRequestType = timeOffRequestTypeRepository.save(timeOffRequestType)

  fun updateTimeOffRequestType(id: UUID, updatedTimeOffRequestType: TimeOffRequestType): TimeOffRequestType? {
    val timeOffRequestType = timeOffRequestTypeRepository.findById(id).orElse(null) ?: return null
    return timeOffRequestTypeRepository.save(
      timeOffRequestType.copy(
        name = updatedTimeOffRequestType.name,
      )
    )
  }
}
