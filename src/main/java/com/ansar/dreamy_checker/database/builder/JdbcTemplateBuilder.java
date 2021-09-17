package com.ansar.dreamy_checker.database.builder;

import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateBuilder {

    private DriverManagerDataSource driverManagerDataSource(ConnectionProperties properties){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        driverManagerDataSource.setUrl("jdbc:sqlserver://" + properties.getHost() + ":" + properties.getPort() +
                "; databaseName=" + properties.getDatabaseName() + ";");
        driverManagerDataSource.setUsername(properties.getUserName());
        driverManagerDataSource.setPassword(properties.getPassword());
        return driverManagerDataSource;
    }

    public JdbcTemplate buildTemplate(ConnectionProperties connectionProperties){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(driverManagerDataSource(connectionProperties));
        return jdbcTemplate;
    }

}
