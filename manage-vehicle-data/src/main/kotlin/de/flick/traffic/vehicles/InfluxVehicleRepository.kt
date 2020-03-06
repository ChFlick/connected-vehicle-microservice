package de.flick.traffic.vehicles

import de.flick.connectors.influxdb.InfluxDBProvider
import org.influxdb.InfluxDB
import org.influxdb.dto.Query
import org.influxdb.impl.InfluxDBResultMapper
import java.time.Instant
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
open class InfluxVehicleRepository
constructor(private val influxDB: InfluxDB) : VehicleRepository {
    @Inject
    constructor (influxDBProvider: InfluxDBProvider) : this(influxDBProvider.get())

    override fun findByStartAndEndTime(start: Instant, end: Instant): List<VehicleDTO> {
        if (start.isAfter(end) || end.isAfter(Instant.now())) {
            return emptyList()
        }

        val query = Query("SELECT latitude, longitude, personNumber, personCapacity, speed, typeId " +
            "FROM vehicle_data WHERE time > $start AND time < $end GROUP BY vehicleId")

        return execute(query)
    }

    override fun findByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO> {
        if (minutesFromNow < 1) {
            return emptyList()
        }

        val query = Query("SELECT latitude, longitude, personNumber, personCapacity, speed, typeId " +
            "FROM vehicle_data WHERE time > now() - ${minutesFromNow}m GROUP BY vehicleId")

        return execute(query)
    }

    private fun execute(query: Query): List<VehicleDTO> {
        val result = influxDB.query(query) ?: return emptyList()
        val resultMapper = InfluxDBResultMapper()

        return resultMapper.toPOJO(result, VehicleDTO::class.java)
    }

}
