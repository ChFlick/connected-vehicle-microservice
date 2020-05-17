package de.flick.connectors.influxdb

import mu.KotlinLogging
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Provider

@ApplicationScoped
class InfluxDBProvider : Provider<InfluxDB> {
    private val logger = KotlinLogging.logger {}

    @ConfigProperty(name = "traffic.influx.username", defaultValue = "admin")
    lateinit var username: String

    @ConfigProperty(name = "traffic.influx.password", defaultValue = "admin")
    lateinit var password: String

    @ConfigProperty(name = "traffic.influx.database", defaultValue = "monaco")
    lateinit var database: String

    @ConfigProperty(name = "traffic.influx.url", defaultValue = "http://localhost:8086")
    lateinit var url: String

    override fun get(): InfluxDB {
        logger.debug { "Accessing influxDb: $username@$url DB:$database" }

        val influxDb = InfluxDBFactory.connect(this.url, this.username, this.password)

        influxDb.enableGzip()
        influxDb.setDatabase(this.database)

        return influxDb
    }
}
