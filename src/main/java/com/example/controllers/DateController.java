package com.example.controllers;

import com.example.clients.RestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class DateController {

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
}
