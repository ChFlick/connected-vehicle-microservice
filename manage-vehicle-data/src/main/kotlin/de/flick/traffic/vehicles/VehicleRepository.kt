package de.flick.traffic.vehicles

import java.time.Instant
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
interface VehicleRepository {
    fun findByStartAndEndTime(start: Instant, end: Instant): List<VehicleDTO>

    fun findByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO>
}
