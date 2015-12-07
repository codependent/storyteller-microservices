package com.codependent.storyteller;

import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@RefreshScope
@SpringBootApplication
public class HtmlGeneratorMsApplication {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${stories-ms-message}")
	private String message;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private ImagesClient imagesClient;
	
	@RequestMapping(value="/stories", params="random=true")
	public String generateHtml(HttpServletResponse response) throws RestClientException, URISyntaxException{
		logger.info("[{}] generateHtml()", message);
		Map<String, String> randomImage = imagesClient.getImage(true, "url");
		
		String html = "<html><body>"+storyService.getRandomStory()+"</body></html>";
		html = String.format(html, randomImage.get("imageUrl"));
		response.setContentType("text/html");
		return html;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(HtmlGeneratorMsApplication.class, args);
    }
}
