package com.sparta.msa_exam.order.order.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductMappingRepository orderProductMappingRepository;


    @Transactional
    public Order createOrder(String name, List<Long> productIds) {
        Order order = Order.builder()
            .name(name)
            .build();

        List<OrderProductMapping> orderProductMappings = productIds.stream()
            .map(productId -> OrderProductMapping.builder()
                .order(order)
                .productId(productId)
                .build())
            .toList();

        order.setProductsIds(orderProductMappings);

        Order savedOrder = orderRepository.save(order);
        orderProductMappingRepository.saveAll(orderProductMappings);

        return savedOrder;
    }

    @Transactional
    public Order addProductToOrder(Long orderId, Long productId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderProductMapping orderProductMapping = OrderProductMapping.builder()
                .order(order)
                .productId(productId)
                .build();
            order.getProductsIds().add(orderProductMapping);
            orderProductMappingRepository.save(orderProductMapping);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
