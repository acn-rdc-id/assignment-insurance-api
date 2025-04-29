package com.azid.auth.backend.AZ.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanDetailsDto {
    private String planName;
    private Double sumAssured;
    private String coverageTerm;
    private Double monthlyPremium;
    private Double yearlyPremium;
}
