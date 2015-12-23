package com.codependent.storyteller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@EnableSidecar
@SpringBootApplication
public class AuthorMsSidecarApplication {
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(AuthorMsSidecarApplication.class).web(true).run(args);
    }
}
