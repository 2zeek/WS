package com.example;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */
@Component
@PropertySources(value = {@PropertySource("classpath:config/db.properties")})
public class CustomJdbcTemplate {

    public CustomJdbcTemplate(JdbcTemplate jdbcTemplate) {
        JdbcTemplate jdbcTemplate1 = jdbcTemplate;
    }
}
