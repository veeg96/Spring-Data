package com.abhishekvermaa10.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class LocaleConfiguration {

    @Bean
    public MessageSource getMessageSource(){
        ReloadableResourceBundleMessageSource src = new ReloadableResourceBundleMessageSource();
        src.setBasename("classpath:messages");
        src.setDefaultEncoding("UTF-8");
        return src;
    }
}
