package com.shakirfattani.employee_management.models
import com.shakirfattani.employee_management.utils.DateUtils
import java.time.LocalDateTime

import jakarta.persistence.*
import java.time.ZoneOffset
import java.util.*

@Entity
@Table(name = "time_off_request_type")  // Use JPA annotations
data class TimeOffRequestType(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

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
