package com.example;

import com.example.clients.OpenWeatherMapClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static com.example.controllers.MergeImagesController.convertTextToGraphic;

/**
 * Created by Nikolay V. Petrov on 30.08.2017.
 */

class WeatherPicGenerator {

    private static OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient();
    private final Path rootLocation = Paths.get(System.getProperty("user.dir") + "\\upload-dir\\vk\\group");

    void generatePic() {

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectNode weatherData = openWeatherMapClient.getWeather();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        Integer currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        //String weather = weatherData.get("weather").asText().toLowerCase();
        String weather = "clear";
        String dayTime = currentHour > 6 && currentHour < 20 ? "day" : "night";
        String weatherPicName = weather + "_" + dayTime;

        String tempText = weatherData.get("temp").asInt() > 0
                ? "+" + weatherData.get("temp").asText() + "°"
                : "1" + weatherData.get("temp").asText() + "°";

        BufferedImage text = convertTextToGraphic(tempText, new Font("Arial", Font.PLAIN, 25));
        BufferedImage icon = null;
        try {
            icon = Scalr.resize(ImageIO.read(
                    new File(System.getProperty("user.dir") + "\\icons\\oneSet\\png\\" + weatherPicName + ".png")),
                    50);
        } catch (IOException e) {
            try {
                icon = Scalr.resize(ImageIO.read(
                        new File(System.getProperty("user.dir") + "\\icons\\oneSet\\png\\celsius.png")),
                        50);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        int w = text.getWidth() + icon.getWidth() + 10;
        int h = Math.max(text.getHeight(), icon.getHeight()) + 10;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(text, 0, 10, null);
        g.drawImage(icon, text.getWidth(), 0, null);

        BufferedImage background = null;
        try {
            background = ImageIO.read(
                    new File( System.getProperty("user.dir") + "\\icons\\oneSet\\background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        BufferedImage background = Scalr.resize(
                ImageIO.read(
                        new File( System.getProperty("user.dir") + "\\icons\\oneSet\\background.jpg")),
                Scalr.Mode.FIT_EXACT, combined.getWidth(), combined.getHeight());*/

        BufferedImage combinedWithBack = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gg = combinedWithBack.getGraphics();
        gg.drawImage(background, 0, 0, null);
//        gg.drawImage(combined, background.getWidth() - combined.getWidth() - 15, 0, null);
        gg.drawImage(combined, 215, 0, null);

        try {
            ImageIO.write(combinedWithBack, "PNG", new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\group\\cover_for_upload.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
