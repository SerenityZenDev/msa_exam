package com.sparta.msa_exam.order.order.order;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private String name;
    private List<Long> productIds;
}
