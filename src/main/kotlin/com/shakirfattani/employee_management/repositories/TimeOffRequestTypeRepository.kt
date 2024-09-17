package com.shakirfattani.employee_management.repositories

import com.shakirfattani.employee_management.models.Employee
import com.shakirfattani.employee_management.models.TimeOffRequestType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TimeOffRequestTypeRepository : JpaRepository<TimeOffRequestType, UUID>
