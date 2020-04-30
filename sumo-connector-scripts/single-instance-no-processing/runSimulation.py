import json
import sys
from datetime import datetime, timedelta

import libsumo as traci
import traci
import traci.constants as tc

SUMO_DB = 'monaco'

# 01.01.2020
START_DATE = datetime(2020, 1, 1)

def run():
    if len(sys.argv) < 2:
        print('Please provide the path to the .sumocfg of a SUMO scenario')
        exit()

    print('started')

    ## GUI / CLI
#     traci.start(["sumo-gui", "-c", sys.argv[1]])
    traci.start(["sumo", "-W", "-c", sys.argv[1]])
    print('traci init')

    step = 0

    startTime = datetime.now()
    print('started at ', startTime.strftime('%Y-%m-%dT%H:%M:%SZ'))
    while traci.simulation.getMinExpectedNumber() > 0:
        traci.simulationStep()
        step += 1

        if(step % 1000 == 0):
            print('\nTime: ', datetime.now().strftime('%Y-%m-%dT%H:%M:%SZ'), '\n')
    traci.close()

    endTime = datetime.now()
    print("Simulation Time:",
          startTime.strftime('%Y-%m-%dT%H:%M:%SZ'),
          endTime.strftime('%Y-%m-%dT%H:%M:%SZ'),
          "differnce:",
          (endTime - startTime).strftime('%Y-%m-%dT%H:%M:%SZ'))

    sys.stdout.flush()


if __name__ == "__main__":
    run()
