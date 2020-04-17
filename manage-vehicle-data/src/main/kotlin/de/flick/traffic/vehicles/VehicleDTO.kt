package de.flick.traffic.vehicles

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.influxdb.annotation.Column
import org.influxdb.annotation.Measurement
import java.time.Instant

@Schema(name = "Vehicle", description = "POJO that represents a vehicle at a specific time.")
@Measurement(name = "vehicle_data")
data class VehicleDTO(
    @field:Schema(implementation = String::class, type = SchemaType.STRING)
    @Column(name = "time")
    var time: Instant = Instant.EPOCH,

    @Column(name = "typeId", tag = true)
    var typeId: String = "",

    @Column(name = "vehicleId", tag = true)
    var vehicleId: String = "",

    @Column(name = "speed")
    var speed: Double = .0,

    @Column(name = "latitude")
    var latitude: Double = .0,

    @Column(name = "longitude")
    var longitude: Double = .0,

    @Column(name = "personCapacity")
    var personCapacity: Int = 0,

    @Column(name = "personNumber")
    var personNumber: Int = 0)
