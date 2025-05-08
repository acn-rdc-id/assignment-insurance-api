package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.ClaimDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimTypeDto;
import com.azid.auth.backend.AZ.Auth.dto.UserDto;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimTypeMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.UserMapper;
import com.azid.auth.backend.AZ.Auth.model.Claim;
import com.azid.auth.backend.AZ.Auth.model.ClaimType;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.User;
import com.azid.auth.backend.AZ.Auth.repository.ClaimRepository;
import com.azid.auth.backend.AZ.Auth.repository.ClaimTypeRepository;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {

    private final UserService userService;
    private final ClaimTypeMapper claimTypeMapper;
    private final PolicyMapper policyMapper;
    private final UserMapper userMapper;

    @Autowired
    ClaimTypeRepository claimTypeRepository;
    @Autowired
    ClaimRepository claimRepository;

    public ClaimService(UserService userService, ClaimTypeMapper claimTypeMapper, PolicyMapper policyMapper, UserMapper userMapper) {
        this.userService = userService;
        this.claimTypeMapper = claimTypeMapper;
        this.policyMapper = policyMapper;
        this.userMapper = userMapper;
    }


    //todo : implement the logic to get the list of claim from the database based on user ID

    //todo : implement the logic to get the detail of claim from the database based on claim ID and user ID
    public ClaimDto getClaimDetailsByClaimId(Long claimId,String userId) {

        ClaimDto claimDto = new ClaimDto();
        User user = userService.getUserByUserId(userId);
        claimDto.setUserDto(userMapper.toDto(user));


        ClaimType claimType = claimTypeRepository.getClaimTypeByClaimId(claimId);
        claimDto.setClaimType(claimTypeMapper.toDto(claimType));

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation Application not found"));

        claimDto.setPolicy(policyMapper.toDto(claim.getPolicy()));
        claimDto.setClaim_date(claim.getClaim_date());
        claimDto.setClaimStatus(claim.getClaimStatus());

        return claimDto;
    }

    //todo : implement the logic to submit claim and upload the document to S3 bucket
}
