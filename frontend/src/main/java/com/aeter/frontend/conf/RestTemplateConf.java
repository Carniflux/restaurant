package com.aeter.frontend.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConf {
    @Bean(name = "appRestClient")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
