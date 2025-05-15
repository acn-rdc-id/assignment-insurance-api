package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
public class ClaimListResponseDto {


    private Long claimId;
    private Long policyId;
    private Date claim_date;
    private String claimStatus;
    private Long claimTypeId;
}
