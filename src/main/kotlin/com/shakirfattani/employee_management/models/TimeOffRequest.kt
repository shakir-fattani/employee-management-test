package com.shakirfattani.employee_management.models
import com.shakirfattani.employee_management.utils.DateUtils
import java.time.LocalDateTime

import jakarta.persistence.*
import java.time.ZoneOffset
import java.util.*

@Entity
@Table(name = "time_off_request")  // Use JPA annotations
data class TimeOffRequest(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "request_type_id", columnDefinition = "BINARY(16)")
    val requestTypeId: UUID,

    @Column(name = "employee_id", columnDefinition = "BINARY(16)")
    val employeeId: UUID,

    @Column(nullable = false)
    val name: String,

    @Column(name = "start_date")
    val startDate: LocalDateTime,

    @Column(name = "end_date")
    val endDate: LocalDateTime,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime? = null
) {
  @PrePersist
  fun onPrePersist() {
    createdAt = DateUtils.now()
    modifiedAt = DateUtils.now()
  }

  @PreUpdate
  fun onPreUpdate() {
    modifiedAt = DateUtils.now()
  }
}
