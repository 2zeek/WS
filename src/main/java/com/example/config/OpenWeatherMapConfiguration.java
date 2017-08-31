package com.example.config;

import com.example.config.properties.OpenWeatherMapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OpenWeatherMapProperties.class)
public class OpenWeatherMapConfiguration {

    @Autowired
    OpenWeatherMapProperties openWeatherMapProperties;
}
