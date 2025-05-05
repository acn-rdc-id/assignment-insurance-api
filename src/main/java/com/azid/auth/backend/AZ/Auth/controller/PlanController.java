package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PlanRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PlanResponseDto;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/get-quotationPlan")
    public ResponseEntity<ApiResponseDto<PlanResponseDto>> getQuotationPlan(
            HttpServletRequest request,
            @RequestBody @Valid PlanRequestDto requestDto) {
        PlanResponseDto responseDto = planService.generatePlan(requestDto);
        return ResponseEntity.ok(new ApiResponseDto<PlanResponseDto>("Success", HttpStatus.OK.value(), "Plan Details retrieved Successfully!!", responseDto));
    }
}
