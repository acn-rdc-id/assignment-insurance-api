package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    // Endpoint to get list a  claim
    @GetMapping("/list")
    public ResponseEntity<ClaimResponseDto> getClaimList() {
        //todo implement logic to fetch claim list
        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
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
