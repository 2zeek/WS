package com.example;

import com.example.clients.OpenWeatherMapClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static com.example.controllers.MergeImagesController.convertTextToGraphic;

/**
 * Created by Nikolay V. Petrov on 30.08.2017.
 */
public class WeatherPicGenerator {

    private static OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient();


    public WeatherPicGenerator() throws IOException {

        ObjectNode weatherData = openWeatherMapClient.getWeather();

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        Integer currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        System.out.println(currentHour);

        //String weather = weatherData.get("weather").asText().toLowerCase();
        String weather = "clear";
        String dayTime = currentHour > 6 && currentHour < 20 ? "day" : "night";
        String weatherPicName = weather + "_" + dayTime;

        BufferedImage text = convertTextToGraphic(weatherData.get("temp").asText(), new Font("Arial", Font.PLAIN, 40));
        BufferedImage icon = Scalr.resize(ImageIO.read(new File( System.getProperty("user.dir") + "\\icons\\oneSet\\" + weatherPicName + ".png")), 100);

        int w = text.getWidth() + icon.getWidth() + 20;
        int h = Math.max(text.getHeight(), icon.getHeight()) + 20;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(text, 0, 10, null);
        g.drawImage(icon, text.getWidth() + 10, 0, null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(System.getProperty("user.dir") + "\\upload-dir\\weather\\path-to-file.png"));

        //write BufferedImage to file
        System.out.println(weatherData.get("weather").asText().toLowerCase());
    }
}
