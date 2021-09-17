package com.ansar.dreamy_checker.model.dao;

import com.ansar.dreamy_checker.model.pojo.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOReader implements ReaderDAO<Product, String> {

    private final JdbcTemplate jdbcTemplate;

    public ProductDAOReader(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> get() {
        return jdbcTemplate.query("SELECT K_Code, Barcode, Name1 from kalaid", rs -> {
            List<Product> products = new ArrayList<>();

            while (rs.next()){
                String id = rs.getString("K_Code");
                String secondId = rs.getString("Barcode");
                String name = rs.getString("Name1");

                products.add(new Product(id, secondId, name));
            }

            return products;
        });
    }

    @Override
    public Product get(String id) {
        return jdbcTemplate.queryForObject("SELECT K_Code, Barcode, Name1 from kalaid where K_Code = ? or Barcode = ?",
                new Object[]{id, id}, new RowMapper<Product>() {
                    @Override
                    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String id = rs.getString("K_Code");
                        String secondId = rs.getString("Barcode");
                        String name = rs.getString("Name1");

                        return new Product(id, secondId, name);
                    }
                });
    }
}
