package com.ansar.dreamy_checker.database.query_executer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ansar.dreamy_checker.Main;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Main.class)
@Slf4j
class FirstIdExtractorTest {

    @Autowired
    private FirstIdExtractor firstIdExtractor;

    @Test
    void testExtractId(){
        final String id1 = "0160250017910003";
        final String id2 = "0160290016690012";
        final String wrongId = "dewwegerg";

        try {
            log.info("First extractedId: {}", firstIdExtractor.extractFirstId(id1));
            log.info("Second extractedId: {}", firstIdExtractor.extractFirstId(id2));
            Assertions.assertNull(firstIdExtractor.extractFirstId(wrongId));
        }catch (Exception exception){
            exception.printStackTrace();
            Assertions.fail();
        }

    }

}
