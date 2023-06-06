package com.security.demo.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productServiceImpl;
    @GetMapping
    public ResponseEntity<?> getProducts(int page, int size) {
        Page<ProductDTO> products = productServiceImpl.findAll(page, size);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/productCode/{productCode}")
    public ResponseEntity<?> getProduct(@PathVariable String productCode) {
        ProductDTO product = productServiceImpl.findByProductCode(productCode);
        return ResponseEntity.ok(product);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registrationProduct(@RequestBody @Valid ProductDTO productRequest) {
        ProductDTO product = productServiceImpl.save(productRequest);
        return ResponseEntity.ok(product);
    }
}
