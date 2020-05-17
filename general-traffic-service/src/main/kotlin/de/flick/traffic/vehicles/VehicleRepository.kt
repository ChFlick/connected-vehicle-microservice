package de.flick.traffic.vehicles

import java.time.ZonedDateTime
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
interface VehicleRepository {
    fun findVehiclesBetween(start: ZonedDateTime, end: ZonedDateTime): List<VehicleDTO>
    fun meanVehicleDataBetween(start: ZonedDateTime, end: ZonedDateTime, meanBy: String): List<VehicleDTO>
    fun findVehiclesByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO>
}
