package com.ansar.dreamy_checker.model.pojo;

import lombok.*;

@Data
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Product {

    private final String name;
    private final String id;
    private String count;
    private final String type;

}
