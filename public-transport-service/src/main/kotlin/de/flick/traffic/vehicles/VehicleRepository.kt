package de.flick.traffic.vehicles

import java.time.ZonedDateTime
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
interface VehicleRepository {
    fun findBusesByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO>
    fun findBusesBetween(start: ZonedDateTime, end: ZonedDateTime): List<VehicleDTO>
    fun meanBusDataBetween(start: ZonedDateTime, end: ZonedDateTime, meanBy: String): List<VehicleDTO>
}
