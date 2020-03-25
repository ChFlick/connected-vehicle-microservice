#!/bin/bash
python3 "$SCRIPT_FOLDER"/sendSimulationDataToLocalInflux.py "$SCRIPT_FOLDER"/MoSTScenario-"$MOST_VERSION"/scenario/most.traci.sumocfg "$INFLUX_HOST" "$INFLUX_PORT"
