package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.service.TermsDeclarationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
@Slf4j

public class TermsDeclarationController {

    private final TermsDeclarationService termsDeclarationService;

    @GetMapping
    public ResponseEntity<List<TermsDeclarationDto>> getAllTermsDeclarations() {
        log.info("TermsDeclarationController: getAllTermsDeclarations STARTED");
        List<TermsDeclarationDto> termsDeclarationDto = termsDeclarationService.getAllTerms();
        log.info("TermsDeclarationController: getAllTermsDeclarations ENDED");
        return ResponseEntity.ok(termsDeclarationDto);
    }


}
