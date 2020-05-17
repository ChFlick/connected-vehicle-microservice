package de.flick.connectors

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.InfluxDBContainer

class InfluxDBResource : QuarkusTestResourceLifecycleManager {
    private val influxDBContainer = InfluxDBContainer<Nothing>().apply {
        withUsername("test")
        withPassword("test")
        withDatabase("test")
    }


    override fun start(): Map<String, String> {
        influxDBContainer.start()

//        InfluxDBMapper(influxDBContainer.newInfluxDB)
//            .save(VehicleDTO(Instant.now(),
//                "car",
//                "123",
//                13.1,
//                123.42,
//                321.24,
//                1,
//                1))

        return mapOf(
            "traffic.influx.url" to influxDBContainer.url,
            "traffic.influx.username" to "test",
            "traffic.influx.password" to "test",
            "traffic.influx.database" to "test"
        )
    }

    override fun stop() {
        influxDBContainer.stop()
    }
}
