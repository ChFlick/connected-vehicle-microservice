import json
import sys
from datetime import datetime, timedelta

import traci
import traci.constants as tc
from influxdb import InfluxDBClient

INFLUX_HOST = 'localhost'
INFLUX_PORT = 8086
SUMO_DB = 'monaco'

SUMO_HOST = 'ip-172-31-47-232.eu-central-1.compute.internal'
SUMO_PORT = 8081

# 01.01.2020
START_DATE = datetime(2020, 1, 1)

def run():
    if len(sys.argv) < 2:
        print('Please provide the path to the .sumocfg of a SUMO scenario')
        exit()

    influx_host = INFLUX_HOST
    influx_port = INFLUX_PORT
    sumo_host = SUMO_HOST
    sumo_port = SUMO_PORT

    if len(sys.argv) == 6:
        influx_host = sys.argv[2]
        influx_port = int(sys.argv[3])
        sumo_host = sys.argv[4]
        sumo_port = int(sys.argv[5])

    print('started')
    client = InfluxDBClient(influx_host, influx_port, 'admin', 'admin')
    client.drop_database(SUMO_DB)
    client.create_database(SUMO_DB)
    client.switch_database(SUMO_DB)
    print('client available')

    ## GUI / CLI
    traci.init(port=sumo_port, numRetries=10, host=sumo_host)

    print('traci init')

    step = 0

    startTime = datetime.now()
    print('started at ', startTime.strftime('%Y-%m-%dT%H:%M:%SZ'))
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
            print('\n', 'Subscriptions: ', len(subscription_results))
            print('Time: ', datetime.now().strftime('%Y-%m-%dT%H:%M:%SZ'))
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

def isInfOrNan(num):
    return float(num) != float(num) or float(num) == float('inf')

def subscriberToInfluxJson(vehicle_id, data) -> json:
    time = (timedelta(0, traci.simulation.getTime()) + START_DATE).strftime('%Y-%m-%dT%H:%M:%SZ')
    latitude, longitude = traci.simulation.convertGeo(
        data[tc.VAR_POSITION][0], data[tc.VAR_POSITION][1])
    speed_kmh = data[tc.VAR_SPEED] * 3.6
    speed_kmh = 0.0 if speed_kmh < 0 else speed_kmh
    return {
        "measurement": "vehicle_data",
        "time": time,
        "tags": {
            "typeId": data[tc.VAR_TYPE],
            "vehicleId": vehicle_id
        },
        "fields": {
            "speed": speed_kmh,
            "latitude": 0.0 if isInfOrNan(latitude) else latitude,
            "longitude": 0.0 if isInfOrNan(longitude) else longitude,
            "personCapacity": data[tc.VAR_PERSON_CAPACITY],
            "personNumber": data[tc.VAR_PERSON_NUMBER],
            "typeId": data[tc.VAR_TYPE],
            "captureTime": datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        }
    }


if __name__ == "__main__":
    run()
