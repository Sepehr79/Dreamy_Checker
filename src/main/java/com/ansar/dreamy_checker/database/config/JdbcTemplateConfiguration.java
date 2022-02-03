package com.ansar.dreamy_checker.database.config;

import com.ansar.dreamy_checker.database.builder.JdbcTemplateBuilder;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import com.ansar.dreamy_checker.security.ConnectionSecurityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Slf4j
@PropertySource("classpath:application.properties")
public class JdbcTemplateConfiguration {

    private static final String HOST = "localhost";
    private static final int PORT = 1433;
    private static final String DATABASE_NAME = "1399";
    private static final String USER = "sepehr";
    private static final String PASSWORD = "123456";

    private final JdbcTemplateBuilder jdbcTemplateBuilder;

    private final ConnectionSecurityManager connectionSecurityManager;

    private ConnectionProperties connectionProperties;

    public JdbcTemplateConfiguration(JdbcTemplateBuilder jdbcTemplateBuilder, ConnectionSecurityManager connectionSecurityManager) {
        this.jdbcTemplateBuilder = jdbcTemplateBuilder;
        this.connectionSecurityManager = connectionSecurityManager;
        try {
            this.connectionProperties = connectionSecurityManager.readConnection();
        } catch (IOException e) {
            log.info(e.getMessage());
            this.connectionProperties = ConnectionProperties.builder()
                    .host(HOST)
                    .password(PASSWORD)
                    .port(PORT)
                    .databaseName(DATABASE_NAME)
                    .userName(USER)
                    .build();
        }
        try {
            connectionSecurityManager.saveConnection(connectionProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public JdbcTemplate defaultJdbcTemplate() {
        return jdbcTemplateBuilder.buildTemplate(
                connectionProperties
        );
    }


}
