package com.xingeyes.boot.dbaccess.jdbc;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class XJDBCConfig {

    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // TODO: 定制JdbcTemplate 逻辑

        return jdbcTemplate;
    }
}
