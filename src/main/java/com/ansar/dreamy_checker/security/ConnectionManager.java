package com.ansar.dreamy_checker.security;

import com.ansar.dreamy_checker.business.json.JsonManager;
import com.ansar.dreamy_checker.model.pojo.ConnectionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ConnectionManager {

    private static final String FILE_NAME = "application";

    private final JsonManager jsonManager;

    private final EncryptedDecrypted encryptedDecrypted;

    private static final Pattern PATTERN = Pattern.compile("\\[.*]");

    /**
     * Save connection with encrypted password
     */
    public void saveConnection(ConnectionProperties connectionProperties) throws IOException {
        jsonManager.createJson(
                connectionProperties.toBuilder()
                        .password(encryptedDecrypted.encrypt(connectionProperties.getPassword()))
                        .build(),
                FILE_NAME,
                ConnectionProperties.class);
    }

    public ConnectionProperties readConnection() throws IOException {
        ConnectionProperties connectionProperties = jsonManager.readJson(FILE_NAME, ConnectionProperties.class);
        String password = connectionProperties.getPassword();
        if (password.matches("\\[.*]")){
            return connectionProperties.toBuilder()
                    .password(password.substring(1, password.length() - 1))
                    .build();
        }
        return connectionProperties.toBuilder()
                .password(encryptedDecrypted.decrypt(connectionProperties.getPassword()))
                .build();
    }

}
