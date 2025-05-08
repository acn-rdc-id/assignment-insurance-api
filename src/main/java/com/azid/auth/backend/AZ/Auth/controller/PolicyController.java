package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationResponseDto;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.exceptions.ErrorResponse;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/policy")

public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PolicyResponseDto>> getAllPolicies(@RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: getAllPolicies STARTED");

        String userId = httpHeaders.getFirst("userId");
        List<PolicyResponseDto> policyList = policyService.getAllPolicies(userId);

        log.info("PolicyController: getAllPolicies ENDED");

        return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Policy List retrieved Successfully!", policyList).getData());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponseDto> getPolicyById(@PathVariable Long id) {

        log.info("PolicyController: getPolicyById STARTED");

        PolicyResponseDto response = policyService.getPolicyById(id);

        log.info("PolicyController: getPolicyById ENDED");

        return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Policy Details retrieved Successfully!!", response).getData());
    }

    @PostMapping("/create-application")
    public ResponseEntity<QuotationApplicationResponseDto> createApplication(@Valid @RequestBody QuotationApplicationRequestDto dto, @RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: createApplication STARTED");


        String userId = httpHeaders.getFirst("userId");
        QuotationApplicationResponseDto responseDto = policyService.createApplication(dto, userId);

        log.info("PolicyController: createApplication ENDED");

        return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Application Created Successfully!", responseDto).getData());
    }

}
