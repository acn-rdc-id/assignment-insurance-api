package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.service.TermsDeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor

public class TermsDeclarationController {

 private final TermsDeclarationService termsDeclarationService;

 @GetMapping
    public List<TermsDeclarationDto>getAllTermsDeclarations(){
     return termsDeclarationService.getAllTerms();
 }



}
