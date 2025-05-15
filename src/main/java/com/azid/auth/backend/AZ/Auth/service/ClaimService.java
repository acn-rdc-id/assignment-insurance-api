package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimMapper;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimTypeMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.model.Claim;
import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.repository.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;
    private final PolicyMapper policyMapper;
    private final ClaimTypeMapper claimTypeMapper;
    public ClaimService (ClaimRepository claimRepository,
                        ClaimMapper claimMapper,
                         PolicyMapper policyMapper,
                         ClaimTypeMapper claimTypeMapper){
        this.claimRepository = claimRepository;
        this.claimMapper = claimMapper;
        this.policyMapper = policyMapper;
        this.claimTypeMapper = claimTypeMapper;
    }

    //todo : implement the logic to get the list of claim from the database based on user ID
    public List<ClaimListResponseDto> getAllClaims(String userId) {
        log.info("start userid check {}", userId);
        return claimRepository.findByUserUserId(userId)
                .stream()
                .map(claim -> {
                    ClaimListResponseDto dto = new ClaimListResponseDto();
                    dto.setClaimId(claim.getClaimId());
                    dto.setPolicyId(claim.getPolicy() != null ? claim.getPolicy().getId() : null);
                    dto.setClaim_date(claim.getClaim_date());
                    dto.setClaimStatus(claim.getClaimStatus());
                    dto.setClaimTypeId(claim.getClaimType() != null ? claim.getClaimType().getClaimTypeId() : null);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    //todo : implement the logic to get the detail of claim from the database based on claim ID and user ID

    //todo : implement the logic to submit claim and upload the document to S3 bucket
}
