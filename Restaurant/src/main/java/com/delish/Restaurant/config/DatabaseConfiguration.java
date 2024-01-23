package com.delish.Restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    public JdbcTemplate jdbcTemplate(final DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
