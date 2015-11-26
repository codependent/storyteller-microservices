package com.codependent.storyteller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ArchimedesApplication {
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(ArchimedesApplication.class).web(true).run(args);
    }
}
