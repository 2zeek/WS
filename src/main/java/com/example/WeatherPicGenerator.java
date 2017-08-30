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

class WeatherPicGenerator {

    private static OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient();


    WeatherPicGenerator() throws IOException {

        ObjectNode weatherData = openWeatherMapClient.getWeather();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        Integer currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        //String weather = weatherData.get("weather").asText().toLowerCase();
        String weather = "clear";
        String dayTime = currentHour > 6 && currentHour < 20 ? "day" : "night";
        String weatherPicName = weather + "_" + dayTime;

        String tempText = weatherData.get("temp").asDouble() > 0
                ? "+" + weatherData.get("temp").asText()
                : "1" + weatherData.get("temp").asText();

        BufferedImage text = convertTextToGraphic(tempText, new Font("Arial", Font.PLAIN, 40));
        BufferedImage icon = Scalr.resize(ImageIO.read(
                new File( System.getProperty("user.dir") + "\\icons\\oneSet\\" + weatherPicName + ".png")),
                100);
        int w = text.getWidth() + icon.getWidth() + 20;
        int h = Math.max(text.getHeight(), icon.getHeight()) + 20;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(text, 0, 10, null);
        g.drawImage(icon, text.getWidth() + 10, 10, null);
        BufferedImage scaledBack = Scalr.resize(
                ImageIO.read(
                        new File( System.getProperty("user.dir") + "\\icons\\oneSet\\background_cyan.png")),
                Scalr.Mode.FIT_EXACT, combined.getWidth(), combined.getHeight());

        BufferedImage combinedWithBack = new BufferedImage(combined.getWidth(), combined.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gg = combinedWithBack.getGraphics();
        gg.drawImage(scaledBack, 0, 0, null);
        gg.drawImage(combined, 0, 0, null);

        ImageIO.write(combinedWithBack, "PNG", new File(System.getProperty("user.dir") + "\\upload-dir\\weather\\upload.png"));
    }
}
