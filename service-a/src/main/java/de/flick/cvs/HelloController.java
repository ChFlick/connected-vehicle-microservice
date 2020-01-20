package de.flick.cvs;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {

    private static char[] password = "admin".toCharArray();

    @GET
    public String sayHello() {
        InfluxDB db = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");

        Query q = new Query("SELECT * FROM NOAA_water_database");

        db.query(q).getResults().forEach(c -> System.out.println(c));

        return "Hello World";
    }
}
