package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.repository.ClaimTypeRepository;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaimService {

    @Autowired
    ClaimTypeRepository claimTypeRepository;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    UserRepository userRepository;


    //todo : implement the logic to get the list of claim from the database based on user ID

    //todo : implement the logic to get the detail of claim from the database based on claim ID and user ID

    //todo : implement the logic to submit claim and upload the document to S3 bucket
    public ClaimResponseDto submitClaim(String policyID, String userID, String claimTypeId, List<MultipartFile> document) {


        // Step 1: Validate the input parameters
        if (policyID == null || userID == null || claimTypeId == null || document == null || document.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // Step 2: Check if the user exists
        if (!userRepository.existsById(userID)) {
            throw new IllegalArgumentException("User not found");
        }

        // Step 3: Check if the policy exists
        if (!policyRepository.existsById(policyID)) {
            throw new IllegalArgumentException("Policy not found");
        }

        // Step 4: Check if the claim type exists
        if (!claimTypeRepository.existsById(claimTypeId)) {
            throw new IllegalArgumentException("Claim type not found");
        }




       // Implement the logic to submit claim and upload the document to S3 bucket



        return null;
    }





    //todo : implement the logic to get the list of claim , claimType and document type; from the database based on user ID and status
    public ClaimInfoResponse getClaimInfoByUserId(Long userId) {
//         Step 1: Get policy IDs
        List<String> policyIds = policyRepository.getPolicyIdsByUserId(userId);

        // Step 2: Get claim type & required documents
        List<Object[]> rows = claimTypeRepository.getClaimTypesWithDocuments();

        Map<Long,  ClaimInfoResponse.ClaimPolicyDocument> claimMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            Long claimTypeId = (Long) row[0];
            String claimType = (String) row[1];
            String claimDescription = (String) row[2];
            String documentName = (String) row[3];
            claimMap.computeIfAbsent(claimTypeId, id -> {
                ClaimInfoResponse.ClaimPolicyDocument doc = new ClaimInfoResponse.ClaimPolicyDocument();
                doc.setClaimTypeId(id);
                doc.setClaimTypeName(claimType);
                doc.setClaimDescription(claimDescription);
                doc.setRequiredDocuments(new ArrayList<>());
                return doc;
            }).getRequiredDocuments().add(documentName);
        }

        ClaimInfoResponse response = new ClaimInfoResponse();
        response.setPolicyId(policyIds);
        response.setClaimPolicyDocument(new ArrayList<>(claimMap.values()));
        return response;
    }

}
