package de.flick.traffic.vehicles

import org.influxdb.annotation.Column
import org.influxdb.annotation.Measurement
import java.time.Instant

@Measurement(name = "vehicle_data")
data class VehicleDTO(
    @Column(name = "time")
    public var time: Instant = Instant.EPOCH,

    @Column(name = "typeId", tag = true)
    public var typeId: String = "",

    @Column(name = "vehicleId", tag = true)
    public var vehicleId: String = "",

    @Column(name = "speed")
    public var speed: Double = .0,

    @Column(name = "latitude")
    public var latitude: Double = .0,

    @Column(name = "longitude")
    public var longitude: Double = .0,

    @Column(name = "personCapacity")
    public var personCapacity: Int = 0,

    @Column(name = "personNumber")
    public var personNumber: Int = 0)
