import traci
import traci.constants as tc
from influxdb import InfluxDBClient
import json
import sys
from datetime import datetime
from dataclasses import dataclass

INFLUX_HOST = 'localhost'
INFLUX_PORT = 8086
SUMO_DB = 'sumo_example'
TRACI_PORT = 8081


def run():
    print('started')
    client = InfluxDBClient(INFLUX_HOST, INFLUX_PORT, 'admin', 'admin')
    client.drop_database(SUMO_DB)
    client.create_database(SUMO_DB)
    client.switch_database(SUMO_DB)
    print('client available')

    traci.init(TRACI_PORT)
    print('traci init')

    step = 0
    while traci.simulation.getMinExpectedNumber() > 0:
        traci.simulationStep()
        # vehicle_ids = [x for x in traci.vehicle.getIDList() if traci.vehicle.getTypeID(x) != 'pkw']
        # if (len(vehicle_ids) > 0):
        #     print('not only pkw')
        vehicles = list(map(vehicleJsonFromVehicleId, traci.vehicle.getIDList()))
        client.write_points(vehicles)
        step += 1
    traci.close()
    sys.stdout.flush()


@dataclass
class VehicleData:
    '''Class for keeping track of vehicle data.'''
    vehicle_id: str
    speed_m_per_s: float
    pos_x: str
    pos_y: str
    type_id: int
    person_capacity: int
    person_number: int

    def to_json(self) -> json:
        time = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        latitude, longitude = traci.simulation.convertGeo(
            self.pos_x, self.pos_y)
        speed_kmh = self.speed_m_per_s * 3.6
        return {
            "measurement": "vehicle_data",
            "time": time,
            "tags": {
                "typeId": self.type_id,
                "vehicleId": self.vehicle_id
            },
            "fields": {
                "speed": speed_kmh,
                "latitude": latitude,
                "longitude": longitude,
                "personCapacity": self.person_capacity,
                "personNumber": self.person_number
            }
        }


def vehicleJsonFromVehicleId(vehicle_id: int) -> VehicleData:
    x, y = traci.vehicle.getPosition(vehicle_id)
    vehicle = VehicleData(vehicle_id,
                          traci.vehicle.getSpeed(vehicle_id),
                          x, y,
                          traci.vehicle.getTypeID(vehicle_id),
                          traci.vehicle.getPersonCapacity(vehicle_id),
                          traci.vehicle.getPersonNumber(vehicle_id))
    return vehicle.to_json()


if __name__ == "__main__":
    run()
