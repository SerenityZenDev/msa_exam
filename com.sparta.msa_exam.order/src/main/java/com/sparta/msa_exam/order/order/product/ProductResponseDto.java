package com.sparta.msa_exam.order.order.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {

    private Long product_id;
    private String name;
    private Integer supply_price;

    // getters and setters
}

