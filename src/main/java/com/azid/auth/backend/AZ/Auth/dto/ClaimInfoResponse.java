package com.azid.auth.backend.AZ.Auth.dto;

import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import com.azid.auth.backend.AZ.Auth.model.DocumentType;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import lombok.Data;

import java.util.List;

@Data
public class ClaimInfoResponse {

    private List<String> policyId;
    private List<ClaimPolicyDocument> claimPolicyDocument;

    @Data
    public static class ClaimPolicyDocument {
        private Long claimTypeId;
        private String claimTypeName;
        private String claimDescription;
        private List<String> requiredDocuments;
    }

}
