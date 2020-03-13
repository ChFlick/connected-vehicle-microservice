package de.flick.traffic

import de.flick.traffic.vehicles.VehicleDTO
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.ZoneId
import java.time.ZonedDateTime

@Schema(name = "Vehicle", description = "POJO that represents a vehicle at a specific time.")
data class VehicleRepresentation(
    val time: ZonedDateTime,
    val typeId: String,
    val vehicleId: String,
    val speed: Double,
    val latitude: Double,
    val longitude: Double,
    val personCapacity: Int,
    val personNumber: Int)

fun VehicleDTO.toVehicleRepresentation() = VehicleRepresentation(
    ZonedDateTime.ofInstant(this.time, ZoneId.systemDefault()),
    this.typeId,
    this.vehicleId,
    this.speed,
    this.latitude,
    this.longitude,
    this.personCapacity,
    this.personNumber
)
