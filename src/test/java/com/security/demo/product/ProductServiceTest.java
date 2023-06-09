package com.security.demo.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Test
    public void givenUnauthenticated_whenCallService_thenThrowsException() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.randomUUID())
                .productCode("001")
                .price(new BigDecimal("10000"))
                .productName("Caffe Latte")
                .build();
        Assertions.assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> productService.save(productDTO));
    }

    @WithMockUser(username="john")
    @Test
    public void givenAuthenticated_whenCallServiceWithSecured_thenOk() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.randomUUID())
                .productCode("001")
                .price(new BigDecimal("10000"))
                .productName("Caffe Latte")
                .build();
        ProductDTO productSave = productService.save(productDTO);
        Assertions.assertEquals("001", productSave.getProductCode());
    }
}
