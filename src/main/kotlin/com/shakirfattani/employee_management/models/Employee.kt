package com.shakirfattani.employee_management.models
import java.time.LocalDateTime

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "employees")  // Use JPA annotations
data class Employee(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val position: String,

    @Column(nullable = false)
    val salary: Float,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime? = null
) {
  @PrePersist
  fun onPrePersist() {
    createdAt = LocalDateTime.now()
    modifiedAt = LocalDateTime.now()
  }

  @PreUpdate
  fun onPreUpdate() {
    modifiedAt = LocalDateTime.now()
  }
}
