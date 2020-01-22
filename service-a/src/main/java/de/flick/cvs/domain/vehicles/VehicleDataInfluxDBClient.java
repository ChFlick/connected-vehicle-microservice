package de.flick.cvs.domain.vehicles;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import javax.inject.Inject;
import java.util.List;

public class VehicleDataInfluxDBClient {
    @Inject
    @ConfigProperty(defaultValue = "admin")
    private String influxPassword;

    @Inject
    @ConfigProperty(defaultValue = "admin")
    private String influxUser;

    @Inject
    @ConfigProperty(defaultValue = "http://localhost:8086")
    private String influxUrl;

    @Inject
    @ConfigProperty(defaultValue = "sumo_example")
    private String vehicleDataDB;

    public List<VehicleDTO> getVehicleData(String query) {
        InfluxDB influx = InfluxDBFactory.connect(this.influxUrl, this.influxUser, this.influxPassword);

        QueryResult result = influx.query(new Query(query, this.vehicleDataDB));
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return resultMapper.toPOJO(result, VehicleDTO.class);
    }
}
