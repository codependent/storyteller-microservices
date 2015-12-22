# storyteller-microservices

Storyteller Microservices implemented with Spring Boot + Spring Cloud Netflix in a high availability environment

* **2 archimedes instances**: acts as Eureka Server. Endpoints: `http://archimedes1:8761` and `http://archimedes2:8762`.
* **1 config-server**: external config server from a [Git Repo](https://github.com/codependent/storyteller-microservices-config). Endpoint: `http://localhost:8888`.
* **1 hystrix-dashboard**: aggretated metrics using Turbine. Endpoint: `http://localhost:10000`.
* **2 images-ms**: mid-tier Eureka Client that returns a random image URL. Endpoint: `http://localhost:[9999|9998]/images?random=true&fields=url`.
* **2 stories-ms**: mid-tier Eureka Client that generates a random HTML story inserting a random image gotten from the previous service. Endpoint: `http://localhost:[9988|9987]/stories?random=true`.
* **2 storyteller-api**: API Eureka Client that invokes stories-ms, using a **Feign** client, to get a random story. Endpoint: `http://localhost:[9977|7799]/api/stories?random=true`.
* **1 gatekeeper**: Zuul edge service that acts as edge service to the internal storyteller-api mid-tier microservice. Endpoint: `http://localhost:9966/api/stories?random=true`.

This structure tries to simulate a scenario like the following:

![Microservices architecture](https://raw.githubusercontent.com/codependent/storyteller-microservices/high-availability/diagram.png)

One API service exposed to clients and two mid tier services, all of them sharing a Eureka server instance (Archimedes) and a ConfigServer that loads the properties from a Git repo.
