Connected Vehicle Service
=========================

![traffic-ui](https://github.com/ChFlick/connected-vehicle-microservice/workflows/traffic-ui/badge.svg)
![manage-vehicle-data](https://github.com/ChFlick/connected-vehicle-microservice/workflows/manage-vehicle-data/badge.svg)

SUMO-InfluxDB Connection
------------------------
For the SUMO-Influx connection you need a [SUMO scenario](https://sumo.dlr.de/docs/Data/Scenarios.html), for example [TAPASCologne](https://sumo.dlr.de/docs/Data/Scenarios/TAPASCologne.html).  
To start the connection of SUMO to the influxDB:
1.  Start docker containers `docker-compose up`
2.  Run the python script `python ./sumo-connector-scripts/sendSimulationDataToLocalInflux.py <PATH_TO_SCENARIO.sumocfg>`
