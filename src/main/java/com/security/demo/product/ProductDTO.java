package com.security.demo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {
    private UUID id;
    @NotNull(message = "productCode cannot be null")
    private String productCode;
    @NotNull(message = "productName cannot be null")
    private String productName;
    @NotNull(message = "price cannot be null")
    private BigDecimal price;
}
