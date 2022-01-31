package com.ansar.dreamy_checker.security;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class EncryptedDecryptedTest {

    private static final EncryptedDecrypted ENCRYPTED_DECRYPTED = new EncryptedDecrypted();

    private static final String PASSWORD = "1234";

    @Test
    void testEncryptingDecrypting(){
        ReflectionTestUtils.setField(ENCRYPTED_DECRYPTED, "counter", 5);

        String encrypted = ENCRYPTED_DECRYPTED.encrypt(PASSWORD);
        log.info(encrypted);
        String decrypted = ENCRYPTED_DECRYPTED.decrypt(encrypted);
        log.info(decrypted);
        assertEquals(PASSWORD, decrypted);
    }

}
