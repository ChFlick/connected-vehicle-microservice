package de.flick.connectors.influxdb

import org.apache.commons.logging.Log
import org.influxdb.InfluxDBFactory
import org.influxdb.dto.Query
import org.jboss.logmanager.Level
import java.util.logging.Logger
import javax.inject.Inject

class InfluxDBConnector
@Inject constructor(private val username: String,
                    private val password: String,
                    private val database: String) {
    fun query() {
        val influxDb = InfluxDBFactory.connect("http://localhost:8086", username, password)
        influxDb.setDatabase(database)

        val query = Query("""SELECT speed FROM vehicledata WHERE time < now()""", database)

        influxDb.query(query).results.forEach { org.jboss.logmanager.Logger.getAnonymousLogger().warning(it.toString()) }
    }
}
