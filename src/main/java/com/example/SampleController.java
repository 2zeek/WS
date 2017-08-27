package com.example;

import com.example.storage.StorageProperties;
import com.example.storage.StorageService;
import com.example.dao.SomeDataDao;
import com.example.model.SomeData;
import com.example.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */

@Controller
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SampleController {

    @Autowired
    public SampleController(SomeDataDao someDataDao) {
        this.someDataDao = someDataDao;
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/random")
    @ResponseBody
    String random() {
        return UUID.randomUUID().toString();
    }

    @RequestMapping("/date")
    @ResponseBody
    String date() {
        return new Date().toString();
    }

    @RequestMapping("/getDate")
    @ResponseBody
    String getDate() {
        return new RestClient().get("date");
    }

    @RequestMapping("/hello")
    @ResponseBody
    String hello(@RequestParam Map<String,String> request) {
        return request.get("greeting") + ", " + request.get("name");
    }

    @RequestMapping("/multiple")
    @ResponseBody
    String multiple(@RequestParam Map<String,String> request) {
        return String.valueOf(Long.parseLong(request.get("first")) * Long.parseLong(request.get("second")));
    }

    @RequestMapping("/quote")
    @ResponseBody
    String quote() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        return  quote.toString();
    }

    private final SomeDataDao someDataDao;

    @RequestMapping("/setData")
    @ResponseBody
    String setData(@RequestParam String data) {
        someDataDao.insert(new SomeData(data));
        return "Success";
    }

    @RequestMapping("/getData")
    @ResponseBody
    String getData(@RequestParam Long id) {
        return someDataDao.findById(id).getData();
    }

    @RequestMapping("/mergeImgs")
    @ResponseBody
    String mergeImgs(@RequestParam Map<String,String> request) throws IOException {
        MergeImg.merge();
        return "Success";
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(SampleController.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
