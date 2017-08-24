package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */
@Component
@PropertySources(value = {@PropertySource("classpath:config/db.properties")})

public class JdbcTemplateImpl {
     private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
