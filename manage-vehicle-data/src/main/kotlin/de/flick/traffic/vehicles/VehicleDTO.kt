package de.flick.traffic.vehicles

import org.influxdb.annotation.Column
import org.influxdb.annotation.Measurement
import java.time.Instant

@Measurement(name = "vehicle_data")
data class VehicleDTO(
    @Column(name = "time")
    private var time: Instant = Instant.EPOCH,

    @Column(name = "typeId", tag = true)
    private var typeId: String = "",

    @Column(name = "vehicleId", tag = true)
    private var vehicleId: String = "",

    @Column(name = "speed")
    private var speed: Double = .0,

    @Column(name = "latitude")
    private var latitude: Double = .0,

    @Column(name = "longitude")
    private var longitude: Double = .0,

    @Column(name = "personCapacity")
    private var personCapacity: Int = 0,

    @Column(name = "personNumber")
    private var personNumber: Int = 0)
