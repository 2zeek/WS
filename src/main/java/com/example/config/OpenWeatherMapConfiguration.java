package com.example.config;

import com.example.config.properties.OpenWeatherMapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherMapConfiguration {

    @Autowired
    OpenWeatherMapProperties openWeatherMapProperties;

    @Bean
    public OpenWeatherMapProperties getOpenWeatherMapProperties() {
        return openWeatherMapProperties;
    }
}
