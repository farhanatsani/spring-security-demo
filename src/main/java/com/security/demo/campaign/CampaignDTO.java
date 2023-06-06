package com.security.demo.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampaignDTO {
    private UUID id;
    @NotNull(message = "title cannot be null")
    private String title;
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "coupon cannot be null")
    private String coupon;
    @NotEmpty(message = "startDate cannot be null")
    private LocalDate startDate;
    @NotEmpty(message = "endDate cannot be null")
    private LocalDate endDate;
    @NotNull(message = "discount cannot be null")
    private Double discount;
}
