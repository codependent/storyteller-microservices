package com.codependent.storyteller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.codependent.storyteller.stream.LogSource;

@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding(LogSource.class)
@RefreshScope
@SpringBootApplication
public class StoryTellerApiApplication {

    public static void main(String[] args) {
    	new SpringApplicationBuilder(StoryTellerApiApplication.class).web(true).run(args);
    }
    
}
