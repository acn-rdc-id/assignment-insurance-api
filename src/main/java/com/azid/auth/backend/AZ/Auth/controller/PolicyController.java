package com.azid.auth.backend.AZ.Auth.controller;


import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponseDto<List<PolicyResponseDto>> getAllPolicies(@RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: getAllPolicies STARTED");

        String userId = httpHeaders.getFirst("userId");
        List<PolicyResponseDto> policyList = policyService.getAllPolicies(userId);

        log.info("PolicyController: getAllPolicies ENDED");

        return new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Policy List retrieved Successfully!", policyList);
    }


    @GetMapping("/{id}")
    public ApiResponseDto<PolicyResponseDto> getPolicyById(@PathVariable Long id) {

        log.info("PolicyController: getPolicyById STARTED");

        PolicyResponseDto response = policyService.getPolicyById(id);

        log.info("PolicyController: getPolicyById ENDED");

        return new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Policy Details retrieved Successfully!!", response);
    }

    @PostMapping("/create-application")
    public ApiResponseDto<QuotationApplicationResponseDto> createApplication(@Valid @RequestBody QuotationApplicationRequestDto dto, @RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: createApplication STARTED");


        String userId = httpHeaders.getFirst("userId");
        QuotationApplicationResponseDto responseDto = policyService.createApplication(dto, userId);

        log.info("PolicyController: createApplication ENDED");

        return new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Application Created Successfully!", responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePolicy(@Valid @PathVariable Long id, @RequestBody PolicyServicingDto dto, @RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: updatePolicyServicing STARTED");

        QuotationApplicationResponseDto responseDto = policyService.updatePolicy(id, dto);

        log.info("PolicyController: updatePolicyServicing ENDED");

        return ResponseEntity.ok(new ApiResponseDto<>("Success", HttpStatus.OK.value(), "", responseDto));
    }

    @PostMapping("/beneficiary")
    public ResponseEntity<ApiResponseDto<BeneficiaryResponseDto>> upsertAll(@Valid @RequestBody BeneficiaryRequestDto req, @RequestHeader HttpHeaders httpHeaders) {

        log.info("PolicyController: upsertAll beneficiaries for policyId={}", req.getPolicyNo());
        String userId = httpHeaders.getFirst("userId");
        BeneficiaryResponseDto result = policyService.upsertAll(req, userId);
        log.info("PolicyController: upsertAll ENDED");

        return ResponseEntity.ok(
                new ApiResponseDto<>("Success", HttpStatus.OK.value(),"Beneficiaries processed successfully", result)
        );
    }
}