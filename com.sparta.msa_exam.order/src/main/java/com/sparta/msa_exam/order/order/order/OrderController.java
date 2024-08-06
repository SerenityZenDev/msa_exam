package com.sparta.msa_exam.order.order.order;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderService.createOrder(orderRequestDto.getName(), orderRequestDto.getProductIds());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId, @RequestBody ProductRequestDto productRequestDto) {
        Order order = orderService.addProductToOrder(orderId, productRequestDto.getProductId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        OrderResponseDto responseDto = OrderResponseDto.builder()
            .orderId(order.getOrderId())
            .productIds(order.getProductsIds().stream()
                .map(OrderProductMapping::getProductId)
                .collect(Collectors.toList()))
            .build();
        return ResponseEntity.ok(responseDto);
    }


}
