package com.sparta.msa_exam.order.order.order;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId;
    private List<Long> productIds;
}
