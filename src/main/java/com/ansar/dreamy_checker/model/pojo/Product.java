package com.ansar.dreamy_checker.model.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
public class Product {

    private @NonNull String id;
    private String name;
    private String price;
    private String anbarId;

}
