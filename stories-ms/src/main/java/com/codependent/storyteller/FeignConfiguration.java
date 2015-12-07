package com.codependent.storyteller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Response;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfiguration {

	@Bean
	public Retryer feignRetrier(){
		return new Retryer.Default();
	}
	
	@Bean
	public ErrorDecoder errorDecoder(){
		return new CustomErrorDecoder();
	}
	
	public class CustomErrorDecoder extends ErrorDecoder.Default{
		@Override
		public Exception decode(String methodKey, Response response) {
			Exception e = super.decode(methodKey, response);
			return e;
		}
	}
}
