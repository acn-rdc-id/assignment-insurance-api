package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.ClaimDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimMapper;
import com.azid.auth.backend.AZ.Auth.service.ClaimService;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    private final ClaimService claimService;

    @Autowired
    private ClaimMapper claimMapper;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Endpoint to get list a  claim
    @GetMapping("/list")
    public ResponseEntity<ClaimResponseDto> getClaimList() {
        //todo implement logic to fetch claim list
        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
    }


    //Endpoint to get detail of claim by claim id
    @GetMapping("/detail/{claimId}")
    public ResponseEntity<ClaimResponseDto> getClaimDetail(@PathVariable Long claimId, @RequestHeader HttpHeaders httpHeaders ) {
        //todo implement logic to fetch claim detail by claim id
        String userId = httpHeaders.getFirst("userId");
        ClaimDto claimDto = claimService.getClaimDetailsByClaimId(claimId, userId);
        ClaimResponseDto responseDto = claimMapper.claimDtoToClaimResponseDTO(claimDto) ;
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/submit")
    public ResponseEntity<ClaimResponseDto> submitClaim(@RequestBody ClaimResponseDto claimRequestDto) {
        //todo implement logic to submit claim
        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
    }
}
