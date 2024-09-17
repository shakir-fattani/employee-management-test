package com.shakirfattani.employee_management.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class DateUtils {
    companion object {
        fun now(): LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)

        fun changeTimeZone(dateTime: LocalDateTime, fromZoneId: ZoneId, toZoneId: ZoneId): LocalDateTime =
            dateTime.atZone(fromZoneId)
                .withZoneSameInstant(toZoneId).toLocalDateTime()

        fun convertUTCToLocalTime(dateTime: LocalDateTime, zoneId: ZoneId): LocalDateTime =
            changeTimeZone(dateTime, ZoneOffset.UTC, zoneId)
    }
}