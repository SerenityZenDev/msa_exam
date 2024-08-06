package com.sparta.msa_exam.product.product.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public String addProduct(ProductRequestDto productRequestDto) {
        Product product = Product.builder()
            .name(productRequestDto.getName())
            .supply_price(productRequestDto.getSupply_price())
            .build();
        productRepository.save(product);
        return product.getName() + " added successfully";
    }
}
