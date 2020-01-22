package de.flick.cvs.domain.vehicles;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;

import java.time.Instant;

@Measurement(name = "vehicledata")
public class VehicleDTO {
    @TimeColumn
    @Column(name = "time")
    private Instant time;

    @Column(name = "vehicleID", tag = true)
    private String vehicleID;

    @Column(name = "speed")
    private double speed;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "noxemission")
    private double noxEmissions;

    @Column(name = "co2emission")
    private double co2Emissions;

    public Instant getTime() {
        return time;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public double getSpeed() {
        return speed;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public double getNoxEmissions() {
        return noxEmissions;
    }

    public double getCo2Emissions() {
        return co2Emissions;
    }
}
