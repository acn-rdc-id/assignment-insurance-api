package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    ClaimService claimService;

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

    @GetMapping("/claimpolicydocument/{userId}")
    public ClaimInfoResponse getPolicyByUserId(@PathVariable Long userId) {
        //todo implement logic to fetch policy by user id


        ClaimInfoResponse claimInfoResponse = claimService.getClaimInfoByUserId(userId);
        return  claimInfoResponse;

    }

    @PostMapping("/submit")
    public ResponseEntity<ClaimResponseDto> submitClaim(@RequestParam("policyID") String policyID,
                                                        @RequestParam("userID") String userID,
                                                        @RequestParam("claimTypeID") String claimTypeID,
                                                        @RequestPart("files") List<MultipartFile> files) {
        //todo implement logic to submit claim

        claimService.submitClaim(policyID, userID, claimTypeID, files);






        ClaimResponseDto responseDto = new ClaimResponseDto();


        return ResponseEntity.ok(responseDto);
    }
}
