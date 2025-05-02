package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanInfoDto {
    private Long id;
    private String planName;
    private Double sumAssured;
    private String coverageTerm;
    private BigDecimal premiumAmount;
    private String premiumMode;
    private String referenceNumber;
}
