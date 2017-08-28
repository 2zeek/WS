package com.example;

import com.example.controllers.VkController;
import com.example.storage.StorageProperties;
import com.example.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */

@Controller
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);



    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        VkController vkController = new VkController();
//        LOG.info(vkController.getWall().toString());
//        LOG.info(vkController.publicPhotoOnTheWall().toString());
//        LOG.info(vkController.publicAvatar().toString());
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }
}
