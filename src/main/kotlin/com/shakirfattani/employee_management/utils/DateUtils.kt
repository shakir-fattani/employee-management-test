package com.shakirfattani.employee_management.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class DateUtils {
    companion object {
        fun now(): LocalDateTime {
            return LocalDateTime.now(ZoneOffset.UTC)
        }
        fun convertToLocalTime(dateTime: LocalDateTime, zoneId: ZoneId): LocalDateTime {
            return dateTime.atZone(ZoneOffset.UTC)
                .withZoneSameInstant(zoneId).toLocalDateTime()
        }
    }
}