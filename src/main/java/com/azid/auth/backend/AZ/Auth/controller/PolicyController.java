package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/policy")

public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService=policyService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PolicyResponseDto>> getAllPolicies(@RequestHeader HttpHeaders httpHeaders){
        String userId = httpHeaders.getFirst("userId");
        log.info(userId);
        List<PolicyResponseDto> policyList = policyService.getAllPolicies(userId);
        return ResponseEntity.ok(policyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponseDto> getPolicyById(@PathVariable Long id) {
        PolicyResponseDto response = policyService.getPolicyById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-application")
    public ResponseEntity<QuotationApplicationResponseDto> createApplication(@RequestBody QuotationApplicationRequestDto dto) {
        QuotationApplicationResponseDto responseDto = policyService.createApplication(dto);
        return ResponseEntity.ok(responseDto);
    }




}
