package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        QuotationApplicationResponseDto quotationApplicationResponseDto = policyService.createPolicy(quotationApplicationDto);
        return ResponseEntity.ok(quotationApplicationResponseDto);
    }
}
