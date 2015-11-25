# storyteller-microservices

Storyteller Microservices implemented with Spring Boot + Spring Cloud Netflix

* archimedes: acts as Eureka Server
* random-image-ms: Eureka Client that returns a random image URL
* html-story-generator-ms: Eureka Client that generates a random story inserting a random image gotten from the previous service
