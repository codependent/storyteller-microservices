package com.codependent.micro;

import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
public class HtmlGeneratorMsApplication {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping("/")
	public String generateHtml(HttpServletResponse response) throws RestClientException, URISyntaxException{
		InstanceInfo ii = discoveryClient.getNextServerFromEureka("RANDOM-IMAGE-MICROSERVICE", false);
		String homePageUrl = ii.getHomePageUrl();
		Map<String, String> imageInfo = restTemplate.exchange(homePageUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String,String>>() {}, new Object[]{}).getBody();
		
		String html = "<html><body>"+storyService.getRandomStory()+"</body></html>";
		html = String.format(html, imageInfo.get("imageUrl"));
		response.setContentType("text/html");
		return html;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(HtmlGeneratorMsApplication.class, args);
    }
}
