package com.security.demo.product;

public class ProductMapper {
    public static Product toProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .productCode(productDTO.getProductCode())
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .build();
        return product;
    }
    public static ProductDTO productDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
        return productDTO;
    }
}
