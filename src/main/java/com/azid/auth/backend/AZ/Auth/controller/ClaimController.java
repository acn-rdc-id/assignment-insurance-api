package com.azid.auth.backend.AZ.Auth.controller;
import com.azid.auth.backend.AZ.Auth.dto.ClaimListResponseDto;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;

import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService=claimService;
    }
    // Endpoint to get list a  claim
    @GetMapping("/list")
    public ResponseEntity<List<ClaimListResponseDto>> getClaimList(@RequestHeader HttpHeaders httpHeaders) {
        //todo implement logic to fetch claim list
        String userId = httpHeaders.getFirst("userId");
        log.info(userId);
        List<ClaimListResponseDto> claimList = claimService.getAllClaims(userId);
        return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Claim List retrieved Successfully!", claimList).getData());
    }


    //Endpoint to get detail of claim by claim id
    @GetMapping("/detail/{claimId}")
    public ResponseEntity<ClaimResponseDto> getClaimDetail(@PathVariable String claimId) {
        //todo implement logic to fetch claim detail by claim id
        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/submit")
    public ResponseEntity<ClaimResponseDto> submitClaim(@RequestBody ClaimResponseDto claimRequestDto) {
        //todo implement logic to submit claim
        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
    }
}
