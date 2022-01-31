package com.ansar.dreamy_checker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@PropertySource("classpath:application.properties")
public class EncryptedDecrypted {

    @Value("${encryption.counter}")
    private int counter;

    public String encrypt(String password){
        for (int i = 0; i < counter; i++){
            password = Base64.getEncoder().encodeToString(password.getBytes());
        }
        return password;
    }

    public String decrypt(String encrypted){
        byte[] out = encrypted.getBytes();
        for (int i = 0; i < counter; i++){
            out = Base64.getDecoder().decode(out);
        }
        return new String(out);
    }

}
