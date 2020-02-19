package de.flick.traffic.vehicles

import de.flick.connectors.InfluxDBResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(InfluxDBResource::class)
class InfluxVehicleRepositoryTest
@Inject constructor(private val influxVehicleRepository: InfluxVehicleRepository) {

//    @Test
//    fun findByStartAndEndTime() {
//    }

    @Test
    fun findByMinutesFromNow() {
        val result = influxVehicleRepository.findByMinutesFromNow(5)

        assertEquals(result, emptyList<VehicleDTO>())
    }
}
