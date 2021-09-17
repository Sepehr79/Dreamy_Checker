package com.ansar.dreamy_checker.model.dao;

import com.ansar.dreamy_checker.Main;
import com.ansar.dreamy_checker.model.pojo.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
class ProductDaoReaderTest {

    @Autowired
    ProductDAOReader productDAOReader;

    @Test
    void testGetSingleProduct(){
        Product product = productDAOReader.get("1000000000009");
        Assertions.assertEquals("خلال دندان بامبو علی", product.getName());
    }

}
