package com.sparta.msa_exam.order.order.order;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Long orderId;
    private String name;
    private List<Long> productIds;
}
