package com.security.demo.campaign;

import com.security.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "t_campaign")
@Getter @Setter @SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign extends BaseEntity {
    private String title;
    private String description;
    private String coupon;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double discount;
}
