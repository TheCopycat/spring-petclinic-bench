# spring-petclinic-bench
Simple Load generator for [Spring pet clinic microservices](https://github.com/spring-petclinic/spring-petclinic-microservices) using gatling.

This is a gatling port of [inspect it ocelot demo load generator](https://github.com/inspectIT/inspectit-ocelot/tree/master/inspectit-ocelot-demo/load). 

## Requirements

For this project to run successfully, you will need at least :
- A JDK 11
- Maven 3 installed
- [Spring et clinic microservices](https://github.com/spring-petclinic/spring-petclinic-microservices) should be running locally (i.e. localhost:8080)

## Run

To start the simulation, simply start

```mvn gatling:test```
