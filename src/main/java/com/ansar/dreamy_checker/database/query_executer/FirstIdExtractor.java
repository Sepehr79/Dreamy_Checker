package com.ansar.dreamy_checker.database.query_executer;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirstIdExtractor {

    private static final String QUERY = "select kalaid.K_Code from KalaId join TblBasket_Kala on kalaid.K_Code = TblBasket_Kala.K_Code where TblBasket_Kala.K_Code_B = ?";

    private final JdbcTemplate jdbcTemplate;

    public String extractFirstId(String secondId){
        try {
            return jdbcTemplate.queryForObject(QUERY, String.class, secondId);
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

}
