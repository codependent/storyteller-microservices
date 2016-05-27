# storyteller-microservices

Storyteller Microservices implemented with Spring Boot + Spring Cloud Netflix

* **archimedes**: acts as Eureka Server. Endpoint: `http://localhost:8761`
* **config-server**: external config server from a [Git Repo](https://github.com/codependent/storyteller-microservices-config). Endpoint: `http://localhost:8888`
* **hystrix-dashboard**: aggretated metrics using Turbine. IMPORTANT: overrides some Turbine files to allow having multiple applications on the same host. [More info](https://github.com/Netflix/Turbine/pull/105). Endpoint: `http://localhost:10000`
* **images-ms**: mid-tier Eureka Client that returns a random image URL. Endpoint: `http://localhost:9999/images?random=true&fields=url`
* **stories-ms**: mid-tier Eureka Client that generates a random HTML story inserting a random image gotten from the previous service. Endpoint: `http://localhost:9988/stories?random=true`
* **storyteller-api**: (master/high-availability branches) API Eureka Client that invokes stories-ms, using a **Feign** client, to get a random story. Endpoint: `http://localhost:9977/api/stories?random=true`
* **gatekeeper**: (zuul/high-availability branches) Zuul edge service that substitutes storyteller-api as the external entry point. Endpoint: `http://localhost:9977/api/stories?random=true`

This structure tries to simulate a scenario like the following:

![Microservices architecture](https://raw.githubusercontent.com/codependent/storyteller-microservices/master/diagram.png)

One API service exposed to clients and two mid tier services, all of them sharing a Eureka server instance (Archimedes) and a ConfigServer that loads the properties from a Git repo.

***Branch differences***

* **master**: storyteller-api acts as a gateway for the internal microservices and uses a Feign client to invoke stories-ms. **[At the moment](https://github.com/Netflix/feign/issues/298) there is no way to specify a fallback method for Feign clients.**
* **no-config-server**: same as master without using a centralized config-server.
* **zuul**: uses a zuul reverse-proxy instead of storyteller-api gateway.
* **high-availability**: high availability environment using two eureka registers, a zuul reverse proxy, and 2 instances of each service.
* **high-availability-rxjava**: Same as above but using reactive programming. It also adds 2 Spring Cloud Stream microservices, a log processor and a central logger.
* **high-availability-rxjava-sidecar**: Polyglot version in which Sidecar integrates a Node.js microservice.
* **consul**: Consul replaces Eureka Server as microservice registry.
