package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@SpringBootApplication
public class SampleController {

    static final Logger LOG = Logger.getLogger(SampleController.class + "_" + Thread.currentThread().getName());

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

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(SampleController.class, args);
    }

}
