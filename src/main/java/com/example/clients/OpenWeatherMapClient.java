package com.example.clients;

import com.example.Application;
import com.example.weather.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
public class OpenWeatherMapClient {

    @Autowired
    private ObjectMapper objectMapper;

    private String server;
    private String params;
    private RestTemplate restTemplate;

    public OpenWeatherMapClient() {
        Properties properties = new Properties();
        try (InputStream is = Application.class.getResourceAsStream("/openweathermap.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        this.restTemplate = new RestTemplate();

        this.server = properties.getProperty("weather.server");
        this.params = properties.getProperty("weather.params");
    }

    @RequestMapping("/weather/getWeather")
    @ResponseBody
    public ResponseEntity<ObjectNode> getWeather() {
        WeatherResponse weatherResponse = restTemplate.getForObject(server + params, WeatherResponse.class);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("temp", weatherResponse.getMain().getTemp().doubleValue());
        jsonObject.put("weather", weatherResponse.getWeather().get(0).getMain());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
