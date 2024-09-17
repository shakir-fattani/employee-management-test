package com.shakirfattani.employee_management.repositories

import com.shakirfattani.employee_management.models.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, UUID> {
  fun findByEmail(email: String): Employee?
}
