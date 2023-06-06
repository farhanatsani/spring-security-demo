package com.security.demo.product;

import com.security.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "t_product")
@Getter @Setter @SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private UUID id;
    private String productCode;
    private String productName;
    private BigDecimal price;
}
