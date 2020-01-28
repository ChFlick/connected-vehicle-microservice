import traci
from influxdb import InfluxDBClient
import json
import sys
from datetime import datetime

host = 'localhost'
port = '8086'


def run():
    print('started')    
    client = InfluxDBClient(host, port, 'admin', 'admin', 'sumo_example')
    print('client available')

    traci.init(8081)

    print('traci init')

    step = 0
    while traci.simulation.getMinExpectedNumber() > 0:
        traci.simulationStep()
        for vehicleID in traci.vehicle.getIDList():
            co2emission = traci.vehicle.getCO2Emission(vehicleID)
            noxemission = traci.vehicle.getNOxEmission(vehicleID)
            if noxemission > 0 or co2emission > 0:
                speed = traci.vehicle.getSpeed(vehicleID) * 3.6
                x, y = traci.vehicle.getPosition(vehicleID)
                longitude, latitude = traci.simulation.convertGeo(x, y)
                json_data = [{'measurement': 'vehicledata', 'time': datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ'), 'tags':{'vehicleID':vehicleID}, 'fields': {'speed': speed,'latitude': latitude, 'longitude': longitude, 'co2emission': co2emission,'noxemission': noxemission}}]
                client.write_points(json_data)
        step += 1
    traci.close()
    sys.stdout.flush()

if __name__ == "__main__":
    run()
