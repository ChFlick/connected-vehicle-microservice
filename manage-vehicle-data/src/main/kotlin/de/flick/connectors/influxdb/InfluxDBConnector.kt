package de.flick.connectors.influxdb

import org.eclipse.microprofile.config.inject.ConfigProperty
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBFactory
import org.influxdb.dto.Query
import org.influxdb.dto.QueryResult
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class InfluxDBConnector {
    @ConfigProperty(name = "username", defaultValue = "admin")
    lateinit var username: String;

    @ConfigProperty(name = "password", defaultValue = "admin")
    lateinit var password: String;

    @ConfigProperty(name = "database", defaultValue = "monaco")
    lateinit var database: String;

    fun query(query: Query): QueryResult? {
        val influxDb = InfluxDBFactory.connect("http://localhost:8086", this.username, this.password)

        influxDb.enableGzip()
        influxDb.setDatabase(this.database)

        return influxDb.query(query)
    }
}
