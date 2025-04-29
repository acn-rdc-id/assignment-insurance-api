package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponseDto> getPolicyById(@PathVariable Long id) {
        PolicyResponseDto response = policyService.getPolicyById(id);
        return ResponseEntity.ok(response);
    }
}
