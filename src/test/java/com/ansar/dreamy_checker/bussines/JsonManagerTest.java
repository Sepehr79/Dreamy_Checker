package com.ansar.dreamy_checker.bussines;

import com.ansar.dreamy_checker.business.json.JsonManager;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonManagerTest {

    private static final JsonManager JSON_MANAGER = new JsonManager();

    @Test
    void jsonManagerTest() throws IOException {
        ConnectionProperties connectionProperties = ConnectionProperties.builder()
                .host("localhost")
                .port(8080)
                .databaseName("1400")
                .password("123456789")
                .build();
        JSON_MANAGER.createJson(connectionProperties, "application", ConnectionProperties.class);

        assertEquals(
                connectionProperties ,JSON_MANAGER.readJson("application", ConnectionProperties.class)
        );
    }

}
