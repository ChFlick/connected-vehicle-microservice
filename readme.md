Connected Vehicle Service
=========================

SUMO-InfluxDB Connection
------------------------
For the SUMO-Influx connection you need a [SUMO scenario](https://sumo.dlr.de/docs/Data/Scenarios.html), for example [TAPASCologne](https://sumo.dlr.de/docs/Data/Scenarios/TAPASCologne.html).  
To start the connection of SUMO to the influxDB:
1.  Start docker containers `docker-compose up`
2.  Start the scenarion `sumo -c ./TAPASCologne-0.32.0/cologne.sumocfg --remote-port 8081`
3.  Run the python script `python ./sumo-connector-scripts/sendSimulationDataToLocalInflux.py`
