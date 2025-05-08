package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.Claim;
import com.azid.auth.backend.AZ.Auth.model.ClaimDocument;
import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ClaimResponseDto {

    private Long claimID;
    private Long policyID;
    private List<Map<String,String>> documentList;
    private ClaimType claimType;
    private LocalDate claim_date;
    private String claimStatus;
}
