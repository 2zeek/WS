package com.example.clients;

import com.example.Application;
import com.example.weather.response.WeatherResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
public class OpenWeatherMapClient {

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
    public String getWeather() {
        WeatherResponse weatherResponse = restTemplate.getForObject(server + params, WeatherResponse.class);


        return weatherResponse.getMain().getTemp().toString();
    }

}
