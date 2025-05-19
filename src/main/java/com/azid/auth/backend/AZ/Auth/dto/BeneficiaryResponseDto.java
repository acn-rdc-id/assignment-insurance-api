package com.azid.auth.backend.AZ.Auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeneficiaryResponseDto {
    private String policyNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BeneficiaryDto> beneficiaries;
}
