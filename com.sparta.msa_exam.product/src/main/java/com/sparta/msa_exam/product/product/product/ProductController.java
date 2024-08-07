package com.sparta.msa_exam.product.product.product;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/products")
    public List<Product> getProducts(HttpServletResponse response) {
        response.addHeader("Server-Port", serverPort);
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.status(200)
            .body(productService.addProduct(productRequestDto));
    }
}
