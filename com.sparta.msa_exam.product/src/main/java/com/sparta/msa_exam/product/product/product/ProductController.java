package com.sparta.msa_exam.product.product.product;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        System.out.println("get Products endpoints is working");
        return null;
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        System.out.println("add product endpoints is working");
        System.out.println(productRequestDto.getName());
        System.out.println(productRequestDto.getSupply_price());
        return null;
    }
}
