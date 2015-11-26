# storyteller-microservices

Storyteller Microservices implemented with Spring Boot + Spring Cloud Netflix

* **archimedes**: acts as Eureka Server. Endpoint: `http://localhost:8761`
* **config-server**: external config server from a [Git Repo](https://github.com/codependent/storyteller-microservices-config). Endpoint: `http://localhost:8888`
* **random-image-ms**: mid-tier Eureka Client that returns a random image URL. Endpoint: `http://localhost:9999/images?random=true&fields=url`
* **random-story-ms**: mid-tier Eureka Client that generates a random HTML story inserting a random image gotten from the previous service. Endpoint: `http://localhost:9988/stories?random=true`
* **storyteller-api**: API Eureka Client that invokes random-story-ms to get random a story. Endpoint: `http://localhost:9977/api/stories?random=true`

This structure tries to simulate a scenario like the following:

![Microservices architecture](https://raw.githubusercontent.com/codependent/storyteller-microservices/master/diagram.png)

One API service exposed to clients and two mid tier services, all of them sharing a Eureka server instance (Archimedes) and a ConfigServer that loads the properties from a Git repo.
