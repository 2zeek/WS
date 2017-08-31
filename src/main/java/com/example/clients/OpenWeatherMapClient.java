package com.example.clients;

import com.example.Application;
import com.example.config.OpenWeatherMapConfiguration;
import com.example.weather.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Import(OpenWeatherMapConfiguration.class)
public class OpenWeatherMapClient {

    private String server;
    private String params;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public OpenWeatherMapClient() {
        Properties properties = new Properties();
        try (InputStream is = Application.class.getResourceAsStream("/config/openweathermap.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.server = properties.getProperty("weather.server");
        this.params = properties.getProperty("weather.params");
    }

    public ObjectNode getWeather() {
        WeatherResponse weatherResponse = restTemplate.getForObject(server + params, WeatherResponse.class);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("temp", weatherResponse.getMain().getTemp().intValue());
        jsonObject.put("weather", weatherResponse.getWeather().get(0).getMain());
        return jsonObject;
    }
}
