package de.flick.cvs.domain.vehicles;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/getCurrentVehicleEmissions")
public class VehicleDataController {

    @Inject
    private VehicleDataInfluxDBClient influxDBClient;

    @GET
    public List<VehicleDTO> getCurrentVehicleEmissions() {
        return influxDBClient.getVehicleData( "SELECT longitude, latitude, co2emission," +
            "noxemission, last(noxemission) FROM vehicledata WHERE time > now() - 15m group by vehicleID");
    }
}
