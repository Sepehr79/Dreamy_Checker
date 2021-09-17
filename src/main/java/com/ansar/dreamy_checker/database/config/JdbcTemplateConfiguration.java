package com.ansar.dreamy_checker.database.config;

import com.ansar.dreamy_checker.database.builder.JdbcTemplateBuilder;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class JdbcTemplateConfiguration {

    @Value("${database.host}")
    private String host;

    @Value("${database.port}")
    private int port;

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.user}")
    private String user;

    @Value("${database.password}")
    private String password;

    private final JdbcTemplateBuilder jdbcTemplateBuilder;

    @Bean
    public JdbcTemplate defaultJdbcTemplate(){
        return jdbcTemplateBuilder.buildTemplate(
                ConnectionProperties.builder()
                        .host(host)
                        .port(port)
                        .userName(user)
                        .password(password)
                        .databaseName(databaseName)
                        .build()
        );
    }

}
