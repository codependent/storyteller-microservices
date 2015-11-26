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
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
@EnableEurekaClient
@RefreshScope
@SpringBootApplication
public class HtmlGeneratorMsApplication {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${random-story-ms-message}")
	private String message;
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@Autowired
	private StoryService storyService;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value="/stories", params="random=true")
	public String generateHtml(HttpServletResponse response) throws RestClientException, URISyntaxException{
		logger.info("[{}] generateHtml()", message);
		InstanceInfo ii = discoveryClient.getNextServerFromEureka("RANDOM-IMAGE-MICROSERVICE", false);
		String homePageUrl = ii.getHomePageUrl();
		Map<String, String> imageInfo = restTemplate.exchange(homePageUrl+"/images?random=true&fields=url", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String,String>>() {}, new Object[]{}).getBody();
		
		String html = "<html><body>"+storyService.getRandomStory()+"</body></html>";
		html = String.format(html, imageInfo.get("imageUrl"));
		response.setContentType("text/html");
		return html;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(HtmlGeneratorMsApplication.class, args);
    }
}
