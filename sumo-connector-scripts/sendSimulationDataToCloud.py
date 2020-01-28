import traci
import paho.mqtt.client as mqtt
import json
import sys

host = 'localhost'
port = '1883'

client = mqtt.Client(client_id="c",
                     clean_session=True,
                     userdata=None,
                     protocol=4,
                     transport="tcp")
topic_to_publish = "telemetry"
topic_to_subscribe = "control/+/+/req/#"

if __name__ == "__main__":
    connect_to_message_gateway()
    run()


def connect_to_message_gateway():
    client.reinitialise(client_id="c", clean_session=True, userdata=None)
    client.username_pw_set(username, password)
    # client.on_connect = on_connect
    client.connect(host, port, 60)
    client.loop_start()


def run():
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
                json_data = json.dumps({'VehicleID': vehicleID, 'Speed': speed, 'Latitude': latitude, 'Longitude': longitude,
                                        'CO2Emission': co2emission, 'NOxEmission': noxemission})
                client.publish(topic_to_publish, json_data, 0, False)
        step += 1
    traci.close()
    sys.stdout.flush()
