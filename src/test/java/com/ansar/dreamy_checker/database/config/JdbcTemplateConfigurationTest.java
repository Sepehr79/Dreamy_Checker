package com.ansar.dreamy_checker.database.config;

import com.ansar.dreamy_checker.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
class JdbcTemplateConfigurationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testDefaultJdbcTemplate(){

        try {
            jdbcTemplate.execute("SELECT K_Code, Barcode, Name1 from kalaid");
        }catch (Exception exception){
            exception.printStackTrace();
            Assertions.fail();
        }

    }

}
