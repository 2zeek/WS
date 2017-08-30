package com.example;

import com.example.controllers.VkController;
import com.example.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */

@Controller
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        WeatherPicGenerator weatherPicGenerator = new WeatherPicGenerator();
        VkController vkController = new VkController();

        Runnable genPic = () -> {
            try {
                weatherPicGenerator.generatePic();
               // vkController.setGroupCover();
                System.out.println(new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(genPic, 0, 30, TimeUnit.MINUTES);
    }
}
