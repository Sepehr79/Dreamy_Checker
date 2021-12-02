package com.ansar.dreamy_checker.model.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
public class Product {

    private final String name;
    private final String id;
    private final String count;
    private final String type;

}
