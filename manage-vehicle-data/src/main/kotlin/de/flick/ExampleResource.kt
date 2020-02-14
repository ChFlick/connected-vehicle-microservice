package de.flick

import de.flick.connectors.influxdb.InfluxDBConnector
import mu.KotlinLogging
import org.influxdb.dto.Query
import org.influxdb.impl.InfluxDBResultMapper
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


private val logger = KotlinLogging.logger {}

@Path("/lastMinute")
@RequestScoped()
class ExampleResource
@Inject
constructor(private val influxDBConnector: InfluxDBConnector) {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        val query = Query("SELECT latitude, longitude, personNumber, personCapacity, speed, typeId FROM vehicle_data WHERE time > now() - 1m GROUP BY vehicleId")
        val result = influxDBConnector.query(query) ?: return "No results"
        val resultMapper = InfluxDBResultMapper()

        val vehicles = resultMapper.toPOJO(result, VehicleDTO::class.java)

        logger.debug { vehicles.size }

        for (vehicle in vehicles) {
            logger.debug { vehicle }
        }

        return "Hallo"
    }
}
