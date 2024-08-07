package com.sparta.msa_exam.order.order.order;

import com.sparta.msa_exam.order.order.product.ProductClient;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductClient productClient;


    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .map(this::toDTO)
            .orElse(null);
    }

    @Transactional
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public OrderDto updateOrder(Long orderId, Long productId) {
        return orderRepository.findById(orderId).map(order -> {
            if (productExists(productId)) {
                OrderProduct orderProduct = OrderProduct.builder()
                    .productId(productId)
                    .order(order)
                    .build();
                orderProductRepository.save(orderProduct);
                order.getProductsIds().add(orderProduct);
                return toDTO(orderRepository.save(order));
            }
            return toDTO(order);
        }).orElse(null);
    }


    private boolean productExists(Long productId) {
        return productClient.getAllProducts().stream()
            .anyMatch(product -> product.getProduct_id().equals(productId));
    }

    private OrderDto toDTO(Order order) {
        return OrderDto.builder()
            .orderId(order.getOrderId())
            .name(order.getName())
            .productIds(order.getProductsIds().stream()
                .map(OrderProduct::getProductId)
                .collect(Collectors.toList()))
            .build();
    }



}
