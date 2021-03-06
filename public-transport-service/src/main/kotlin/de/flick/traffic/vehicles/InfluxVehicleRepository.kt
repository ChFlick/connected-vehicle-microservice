package de.flick.traffic.vehicles

import de.flick.connectors.influxdb.InfluxDBProvider
import mu.KotlinLogging
import org.influxdb.InfluxDB
import org.influxdb.dto.Query
import org.influxdb.impl.InfluxDBResultMapper
import java.time.ZonedDateTime
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class InfluxVehicleRepository
private constructor(private val influxDB: InfluxDB) : VehicleRepository {
    private val logger = KotlinLogging.logger {}

    @Inject
    constructor (influxDBProvider: InfluxDBProvider) : this(influxDBProvider.get())

    override fun findBusesBetween(start: ZonedDateTime, end: ZonedDateTime): List<VehicleDTO> {
        if (start.isAfter(end) || end.isAfter(ZonedDateTime.now())) {
            return emptyList()
        }

        val query = Query("SELECT latitude, longitude, personNumber, personCapacity, speed, typeId " +
            "FROM vehicle_data " +
            "WHERE time > '${start.toInstant()}' AND time < '${end.toInstant()}' AND typeId = 'bus' " +
            "GROUP BY vehicleId")

        logger.info { "Querying " + query.command }

        return execute(query)
    }

    override fun meanBusDataBetween(start: ZonedDateTime, end: ZonedDateTime, meanBy: String): List<VehicleDTO> {
        if (start.isAfter(end) || end.isAfter(ZonedDateTime.now())) {
            return emptyList()
        }

        val query = Query("SELECT " +
            "mean(personNumber) as personNumber, " +
            "mean(speed) as speed, " +
            "mean(personCapacity) as personCapacity " +
            "FROM vehicle_data " +
            "WHERE time > '${start.toInstant()}' AND time < '${end.toInstant()}' AND typeId = 'bus' " +
            "GROUP BY vehicleId, time($meanBy)")

        logger.info { "Querying " + query.command }

        return execute(query)
    }

    override fun findBusesByMinutesFromNow(minutesFromNow: Int): List<VehicleDTO> {
        if (minutesFromNow < 1) {
            return emptyList()
        }

        val query = Query("SELECT latitude, longitude, personNumber, personCapacity, speed, typeId " +
            "FROM vehicle_data " +
            "WHERE time > now() - ${minutesFromNow}m AND typeId = 'bus' " +
            "GROUP BY vehicleId")

        logger.info { "Querying " + query.command }

        return execute(query)
    }

    private fun execute(query: Query): List<VehicleDTO> {
        val result = influxDB.query(query) ?: return emptyList()
        return InfluxDBResultMapper().toPOJO(result, VehicleDTO::class.java)
    }

}
