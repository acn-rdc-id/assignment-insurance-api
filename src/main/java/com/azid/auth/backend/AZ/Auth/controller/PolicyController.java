package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policy")

public class PolicyController {
    @Autowired
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService=policyService;
    }

    @PostMapping("/create")
    public ResponseEntity<QuotationApplicationResponseDto> createQuotationApplication(@Valid @RequestBody QuotationApplicationDto quotationApplicationDto){
        QuotationApplicationResponseDto quotationApplicationResponseDto = policyService.createQuotationApplication(quotationApplicationDto);
        return ResponseEntity.ok(quotationApplicationResponseDto);
    }

    @PostMapping("/create-application")
    public ResponseEntity<QuotationApplicationResponseDto> createApplication(@RequestBody QuotationApplicationRequestDto dto) {
        QuotationApplicationResponseDto responseDto = policyService.createApplication(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDto> getPolicyById(@PathVariable Long id) {
        PolicyDto response = policyService.getPolicyById(id);
        return ResponseEntity.ok(response);
    }
}
