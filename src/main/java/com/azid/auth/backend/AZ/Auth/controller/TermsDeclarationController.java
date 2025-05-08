package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.dtos.ApiResponseDto;
import com.azid.auth.backend.AZ.Auth.service.TermsDeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
@Slf4j


public class TermsDeclarationController {

    private final TermsDeclarationService termsDeclarationService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<TermsDeclarationDto>>> getActiveTerms(
            @RequestHeader HttpHeaders httpHeaders) {

        log.info("TermsDeclarationController: getActiveTerms STARTED");

        List<TermsDeclarationDto> termsDeclarationDtos = termsDeclarationService.getAllTerms();

        log.info("TermsDeclarationController: getActiveTerms ENDED");
        return ResponseEntity.ok(
                new ApiResponseDto<>("Success", HttpStatus.OK.value(), "Active terms retrieved successfully", termsDeclarationDtos)
        );
}}
