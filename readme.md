Connected Vehicle Service
=========================

![traffic-ui](https://github.com/ChFlick/connected-vehicle-microservice/workflows/traffic-ui/badge.svg)
![public-transport-service](https://github.com/ChFlick/connected-vehicle-microservice/workflows/public-transport-service/badge.svg)

SUMO-InfluxDB Connection
------------------------
For the SUMO-Influx connection you need a [SUMO scenario](https://sumo.dlr.de/docs/Data/Scenarios.html), for example [TAPASCologne](https://sumo.dlr.de/docs/Data/Scenarios/TAPASCologne.html).  
To start the connection of SUMO to the influxDB:
1.  Start docker containers `docker-compose up`
2.  Run the python script `python ./sumo-connector-scripts/sendSimulationDataToLocalInflux.py <PATH_TO_SCENARIO.sumocfg>`

Services
--------

The **public-transport-service** service is responsible for reading and processing the data coming from the InfluxDB.

The **traffic-ui** service simple serves a react frontend which accesses data via the public-transport-service and displays it.

Both services are build on top of quarkus and can be started either compiling them and running the resulting jar file, or by running the quarkusDev gradle goal.
