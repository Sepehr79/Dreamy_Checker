package com.ansar.dreamy_checker.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class UniqueProductProperty {

    private final String id;
    private final String secondId;

}
