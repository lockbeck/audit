package com.lockbeck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  {
    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://10.10.40.60:4200",
                                "http://localhost:4200"
                        )
                        .allowCredentials(true)
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.PUT.name()
                        );
            }
        };
    }


}