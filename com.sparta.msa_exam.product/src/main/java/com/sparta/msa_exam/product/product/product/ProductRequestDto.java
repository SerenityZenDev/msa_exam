package com.sparta.msa_exam.product.product.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private int supply_price;
}
