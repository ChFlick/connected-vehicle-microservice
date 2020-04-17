package de.flick.traffic.vehicles

import java.time.Instant
import java.time.ZonedDateTime
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
interface VehicleRepository {
    fun findByStartAndEndTime(start: Instant, end: Instant): List<VehicleDTO>
    fun findFromTillNow(start: Instant): List<VehicleDTO>
    fun findByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO>

    fun findBusesByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO>
    fun findBusesBetween(start: ZonedDateTime, end: ZonedDateTime): List<VehicleDTO>
}
