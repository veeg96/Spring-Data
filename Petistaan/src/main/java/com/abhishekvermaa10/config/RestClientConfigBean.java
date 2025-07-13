package com.abhishekvermaa10.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfigBean {

    @Bean
    public RestClient getRestClientBean(){
        return RestClient.builder().build();//use this rest client in petServiceImpl to translate messages
    }
}
