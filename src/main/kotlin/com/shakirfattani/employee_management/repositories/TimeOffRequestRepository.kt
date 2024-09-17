package com.shakirfattani.employee_management.repositories

import com.shakirfattani.employee_management.models.TimeOffRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TimeOffRequestRepository : JpaRepository<TimeOffRequest, UUID> {
    fun findAllByEmployeeId(employeeId: UUID): List<TimeOffRequest>
}
