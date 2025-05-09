package com.azid.auth.backend.AZ.Auth.controller;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.azid.auth.backend.AZ.Auth.dto.ClaimDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimMapper;
import com.azid.auth.backend.AZ.Auth.service.AwsS3Service;
import com.azid.auth.backend.AZ.Auth.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    ClaimService claimService;

    @Autowired
    AwsS3Service awsS3Service;

    @Autowired
    ClaimMapper claimMapper;

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

    @GetMapping("/claimpolicydocument/{userId}")
    public ResponseEntity<ApiResponseDto<ClaimInfoResponse>> getPolicyByUserId(@PathVariable String userId) {
        ApiResponseDto<ClaimInfoResponse> apiResponseDto = null;

        log.info("Start process for get claim policy document for userId: {}", userId);
        try {
            if (userId == null || userId.isEmpty()) {
                log.error("User ID is null or empty");
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.BAD_REQUEST.value(), "User ID cannot be null or empty", null);
            }

            log.info("Fetching claim policy document for userId: {}", userId);
            ClaimInfoResponse claimInfoResponse = claimService.getClaimInfoByUserId(userId);
            if(claimInfoResponse == null) {
                log.error("No claim policy document found for userId: {}", userId);
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.NOT_FOUND.value(), "No claim policy document found", null);
            } else {
                log.info("Claim policy document retrieved successfully for userId: {}", userId);
                log.info("End process for get claim policy document");
                apiResponseDto = new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Claim Policy Document Retrieved Successfully!", claimInfoResponse);
            }

        }catch (Exception e) {
            log.error("Error occurred while retrieving claim policy document for userId: {}. Error: {}", userId, e.getMessage());
            apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null);
        }

        return ResponseEntity.ok(apiResponseDto);

    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponseDto<ClaimInfoResponse>> submitClaim(@RequestParam("policyID") String policyID,
                                                        @RequestParam("userID") String userID,
                                                        @RequestParam("claimTypeID") String claimTypeID,
                                                        @RequestPart("files") List<MultipartFile> files) {
        ApiResponseDto  apiResponseDto = null;

        log.info("Start process for submit claim policy document for userId: {}", userID);
        try{
            if (policyID == null || policyID.isEmpty()) {
                log.error("Policy ID is null or empty");
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.BAD_REQUEST.value(), "Policy ID cannot be null or empty", null);
            }
            if (userID == null || userID.isEmpty()) {
                log.error("User ID is null or empty");
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.BAD_REQUEST.value(), "User ID cannot be null or empty", null);
            }
            if (claimTypeID == null || claimTypeID.isEmpty()) {
                log.error("Claim Type ID is null or empty");
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.BAD_REQUEST.value(), "Claim Type ID cannot be null or empty", null);
            }

            ClaimResponseDto responseDto = claimService.submitClaim(policyID, userID, claimTypeID, files);
            if(responseDto == null) {
                log.info("Claim policy document fail submitted for userId: {}", userID);
                apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.NOT_FOUND.value(), "No claim policy document found", null);
            } else {
                log.info("Claim policy document submitted successfully for userId: {}", userID);
                log.info("End process for submit claim policy document");
                apiResponseDto = new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Claim Policy Document Submitted Successfully!", responseDto);
            }
        }
        catch (Exception e) {
            log.error("Error occurred while validating input parameters for claim submission: {}", e.getMessage());
            apiResponseDto = new ApiResponseDto<>("Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null);
        }
        return ResponseEntity.ok(apiResponseDto);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("key") String keyName) {
        try (S3ObjectInputStream s3is = awsS3Service.downloadFile(keyName))
        {
            byte[] content = IOUtils.toByteArray(s3is);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(keyName.substring(keyName.lastIndexOf("/") + 1))
                    .build());

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + keyName, e);
        }
    }

}
