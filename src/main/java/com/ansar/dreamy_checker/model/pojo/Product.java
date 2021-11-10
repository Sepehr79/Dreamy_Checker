package com.ansar.dreamy_checker.model.pojo;

import lombok.*;

@Data
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
public class Product {

    private final String name;
    private final String id;
    private final String secondId;

}
