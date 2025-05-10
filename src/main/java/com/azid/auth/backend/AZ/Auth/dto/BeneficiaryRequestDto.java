package com.azid.auth.backend.AZ.Auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeneficiaryRequestDto {
    private Long id;
    private PolicyDto policy;
    private String beneficiaryName;
    private String relationshipToInsured;
}
