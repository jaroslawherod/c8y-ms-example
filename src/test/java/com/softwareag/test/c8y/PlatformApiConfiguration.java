package com.softwareag.test.c8y;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class PlatformApiConfiguration {

    private final static Logger log = LoggerFactory.getLogger(PlatformApiConfiguration.class);

    @Bean(name = "platformApi")
    public RestTemplate platformApi(
            @Value("${platform.baseURL}") String baseUrl,
            @Value("${platform.username}") String username,
            @Value("${platform.password}") String password) {
        log.info("Starting platform with base url: {} and credentials {}/{}",baseUrl,username,password);

        return new RestTemplateBuilder()
                .basicAuthentication(username, password)
                .additionalInterceptors(new JsonMimeInterceptor())
                .rootUri(baseUrl)
                .build();
    }

    public class JsonMimeInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            if(!headers.containsKey("Accept")) {
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            }
            if(!headers.containsKey("Content-Type")) {
                headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
            }
            return execution.execute(request, body);
        }
    }
}
