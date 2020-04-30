#!/bin/bash
python3 -u "$SCRIPT_FOLDER"/sendSimulationDataToLocalInflux.py "$SCRIPT_FOLDER"/MoSTScenario-"$MOST_VERSION"/scenario/most.traci.sumocfg "$INFLUX_HOST" "$INFLUX_PORT" "$SUMO_HOST" "$SUMO_PORT"
