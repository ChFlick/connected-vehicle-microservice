#!/bin/bash
sumo -v -c "$SCRIPT_FOLDER"/MoSTScenario-"$MOST_VERSION"/scenario/most.traci.sumocfg --remote-port 8081
