package com.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class RandomGeneratorController {

    @RequestMapping("/random")
    @ResponseBody
    String random() {
        return UUID.randomUUID().toString();
    }
}
