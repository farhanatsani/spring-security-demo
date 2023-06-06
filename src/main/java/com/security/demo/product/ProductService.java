package com.security.demo.product;

import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO save(ProductDTO productDTO);
    Page<ProductDTO> findAll(int page, int size);
    ProductDTO findByProductCode(String productCode);
}
