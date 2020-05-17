package de.flick.traffic.vehicles

import de.flick.connectors.InfluxDBResource
import de.flick.connectors.influxdb.InfluxDBProvider
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(InfluxDBResource::class)
class InfluxVehicleRepositoryTest {
    @Inject
    lateinit private var influxVehicleRepository: InfluxVehicleRepository

    @Test
    fun findByMinutesFromNow() {
        val result = influxVehicleRepository.findByMinutesFromNow(5)

        assertThat(result).isEmpty()
    }
}
