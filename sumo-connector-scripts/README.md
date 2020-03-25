Dockerfile to run SUMO and send the data to an InfluxDB
=======================================================

build
-----
`docker build -t sumo-influx:latest .`

run
---
`docker run -it -e INFLUX_HOST=somedomain.de -e INFLUX_PORT=123456 sumo-influx`
