package com.example.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "file:${config.directory}/openweathermap.properties")
@ConfigurationProperties(prefix = "weather")
@Configuration
public class OpenWeatherMapProperties {

    private String server;
    private String params;

    public OpenWeatherMapProperties openWeatherMapProperties() {
        OpenWeatherMapProperties newProp = new OpenWeatherMapProperties();
        newProp.server = System.getProperty("weather.server");
        newProp.params = System.getProperty("weather.params");
        return newProp;
    }

    public String getServer() {
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
