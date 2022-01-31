package com.ansar.dreamy_checker.security;

import com.ansar.dreamy_checker.business.json.JsonManager;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionManagerTest {

    @Test
    void testConnectionManager() throws IOException {
        EncryptedDecrypted encryptedDecrypted = new EncryptedDecrypted();
        ReflectionTestUtils.setField(encryptedDecrypted, "counter", 5);
        JsonManager jsonManager = new JsonManager();
        ConnectionManager connectionManager = new ConnectionManager(
                jsonManager,
                encryptedDecrypted
        );

        ConnectionProperties connectionProperties = ConnectionProperties.builder()
                .host("localhost")
                .port(1433)
                .password("1234")
                .databaseName("test")
                .build();
        connectionManager.saveConnection(
                connectionProperties
        );

        assertEquals(encryptedDecrypted.encrypt("1234"),
            jsonManager.readJson("application", ConnectionProperties.class)
                    .getPassword()
        );

        assertEquals(connectionProperties ,connectionManager.readConnection());
    }

    @Test
    void testSaveConnectionWithUserPassword() throws IOException {
        ConnectionProperties connectionProperties = ConnectionProperties.builder()
                .password("[123456789]").build();
        JsonManager jsonManager = new JsonManager();
        EncryptedDecrypted encryptedDecrypted = new EncryptedDecrypted();
        ReflectionTestUtils.setField(encryptedDecrypted, "counter", 6);
        jsonManager.createJson(connectionProperties, "application", ConnectionProperties.class);

        ConnectionManager connectionManager = new ConnectionManager(jsonManager, encryptedDecrypted);
        assertEquals("123456789", connectionManager.readConnection().getPassword());
    }

}
