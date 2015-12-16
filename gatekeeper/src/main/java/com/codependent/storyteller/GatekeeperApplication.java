package com.codependent.storyteller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class GatekeeperApplication {

    public static void main(String[] args) {
    	new SpringApplicationBuilder(GatekeeperApplication.class).web(true).run(args);
    }
}
