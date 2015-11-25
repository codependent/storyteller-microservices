# storyteller-microservices

Storyteller Microservices implemented with Spring Boot + Spring Cloud Netflix

* archimedes: acts as Eureka Server
* random-image-ms: Mid-tier Eureka Client that returns a random image URL
* random-story-ms: Mid-tier Eureka Client that generates a random HTML story inserting a random image gotten from the previous service
* storyteller-api: API Eureka Client that invokes random-story-ms to get random a story
