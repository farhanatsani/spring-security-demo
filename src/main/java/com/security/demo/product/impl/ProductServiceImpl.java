package com.security.demo.product.impl;

import com.security.demo.base.ResponseMessage;
import com.security.demo.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @PreAuthorize("authenticated")
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = ProductMapper.toProduct(productDTO);
        productRepository.saveAndFlush(product);
        return ProductMapper.productDTO(product);
    }
    @Override
    public Page<ProductDTO> findAll(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of((page - 1), size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::productDTO);
    }
    @Override
    public ProductDTO findByProductCode(String productCode) {
        Optional<Product> productByCodeOptional = productRepository.findByProductCode(productCode);
        if(productByCodeOptional.isEmpty()) {
            throw new NullPointerException(ResponseMessage.PRODUCT_NOT_FOUND);
        }
        ProductDTO productDTO = ProductMapper.productDTO(productByCodeOptional.get());
        return productDTO;
    }
}
