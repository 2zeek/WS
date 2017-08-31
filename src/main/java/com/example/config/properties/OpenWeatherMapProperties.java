package com.example.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:${config.directory}/openweathermap.properties")
@ConfigurationProperties(prefix = "weather")
@Configuration
public class OpenWeatherMapProperties {

    @Value("${weather.server}")
    private String server;
    @Value("${weather.params}")
    private String params;

    public String getServer() {
        System.out.println((System.getProperty("weather.server")));
        System.out.println(System.getProperty("weather.params"));
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
