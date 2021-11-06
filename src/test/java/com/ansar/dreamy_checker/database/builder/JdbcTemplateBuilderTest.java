package com.ansar.dreamy_checker.database.builder;


import com.ansar.dreamy_checker.Main;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
class JdbcTemplateBuilderTest {

    @Autowired
    JdbcTemplateBuilder jdbcTemplateBuilder;

    @Test
    void testDatabaseConnection(){
        JdbcTemplate jdbcTemplate = jdbcTemplateBuilder.buildTemplate(
                ConnectionProperties.builder()
                        .host("localhost")
                        .port(1433)
                        .databaseName("1399")
                        .userName("sepehr")
                        .password("s134s134")
                        .build()
        );

        try {
            jdbcTemplate.execute("SELECT K_Code, Barcode, Name1 from kalaid");
        }catch (Exception exception){
            exception.printStackTrace();
            Assertions.fail();
        }


    }

}
