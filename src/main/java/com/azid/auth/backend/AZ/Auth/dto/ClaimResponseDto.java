package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class ClaimResponseDto {

    @JsonIgnoreProperties
    private Long id;
    private PolicyDto policy;
    private String claimType;
    private Date claim_date;
    private String claimStatus;
}
