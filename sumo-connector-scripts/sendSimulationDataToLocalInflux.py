import json
import sys
from dataclasses import dataclass
from datetime import datetime

import libsumo as traci
import traci
import traci.constants as tc
from influxdb import InfluxDBClient

INFLUX_HOST = 'localhost'
INFLUX_PORT = 8086
SUMO_DB = 'monaco'
TRACI_PORT = 8081


def run():
    if len(sys.argv) < 2:
        print('Please provide the path to the .sumocfg of a SUMO scenario')
        exit()

    print('started')
    client = InfluxDBClient(INFLUX_HOST, INFLUX_PORT, 'admin', 'admin')
    client.drop_database(SUMO_DB)
    client.create_database(SUMO_DB)
    client.switch_database(SUMO_DB)
    print('client available')

    ## GUI / CLI
    traci.start(["sumo-gui", "-c", sys.argv[1]])
    # traci.start(["sumo", "-c", sys.argv[1]])
    print('traci init')

    step = 0

    startTime = datetime.now()

    while traci.simulation.getMinExpectedNumber() > 0:
        traci.simulationStep()
        departed_ids = traci.simulation.getDepartedIDList()

        # ALL VEHICLES
        [subscribe(x) for x in departed_ids]

        # ONLY BUS
        # [subscribe(x) for x in departed_ids if traci.vehicle.getTypeID(x) == 'bus']

        subscription_results = traci.vehicle.getAllSubscriptionResults()
        vehicles = [subscriberToInfluxJson(
            id, subscription_results[id]) for id in subscription_results]
        client.write_points(vehicles)
        step += 1

        if(step % 1000 == 0):
            print("\n", "Time:", traci.simulation.getTime())
            print("Vehicles:", traci.vehicle.getIDCount())
    traci.close()

    endTime = datetime.now()
    print("Simulation Time:",
          startTime.strftime('%Y-%m-%dT%H:%M:%SZ'),
          endTime.strftime('%Y-%m-%dT%H:%M:%SZ'),
          "differnce:",
          (endTime - startTime).strftime('%Y-%m-%dT%H:%M:%SZ'))

    sys.stdout.flush()


def subscribe(vehicle_id):
    traci.vehicle.subscribe(vehicle_id, [
                            tc.VAR_SPEED, tc.VAR_POSITION, tc.VAR_TYPE, tc.VAR_PERSON_CAPACITY, tc.VAR_PERSON_NUMBER])


def subscriberToInfluxJson(vehicle_id, data) -> json:
    time = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
    latitude, longitude = traci.simulation.convertGeo(
        data[tc.VAR_POSITION][0], data[tc.VAR_POSITION][1])
    speed_kmh = data[tc.VAR_SPEED] * 3.6
    return {
        "measurement": "vehicle_data",
        "time": time,
        "tags": {
            "typeId": data[tc.VAR_TYPE],
            "vehicleId": vehicle_id
        },
        "fields": {
            "speed": speed_kmh,
            "latitude": latitude,
            "longitude": longitude,
            "personCapacity": data[tc.VAR_PERSON_CAPACITY],
            "personNumber": data[tc.VAR_PERSON_NUMBER],
            "typeId": data[tc.VAR_TYPE]
        }
    }


if __name__ == "__main__":
    run()
