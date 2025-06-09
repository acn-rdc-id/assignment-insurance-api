package com.azid.auth.backend.AZ.Auth.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ClaimInfoResponse {

    private List<Map<String,String>> policyInfo;
    private List<ClaimPolicyDocument> claimPolicyDocument;

    @Data
    public static class ClaimPolicyDocument {
        private Long claimTypeId;
        private String claimTypeName;
        private String claimDescription;
        private List<String> requiredDocuments;
    }

}
