package com.sparta.msa_exam.product.product.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(cacheNames = "productAllCache", key = "methodName")
    public List<Product> getAllProducts() {
        System.out.println("does not work cache");
        return productRepository.findAll();
    }

    @CacheEvict(cacheNames = "productAllCache", key = "methodName")
    public String addProduct(ProductRequestDto productRequestDto) {
        Product product = Product.builder()
            .name(productRequestDto.getName())
            .supply_price(productRequestDto.getSupply_price())
            .build();
        productRepository.save(product);
        return product.getName() + " added successfully";
    }
}
