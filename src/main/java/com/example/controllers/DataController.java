package com.example.controllers;

import com.example.dao.SomeDataDao;
import com.example.model.SomeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private SomeDataDao someDataDao;

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
}
