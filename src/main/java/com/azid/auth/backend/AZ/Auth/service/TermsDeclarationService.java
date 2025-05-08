package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.TermsDeclarationMapper;
import com.azid.auth.backend.AZ.Auth.repository.TermsDeclarationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TermsDeclarationService {

    private final TermsDeclarationRepository termsDeclarationRepository;
    private final TermsDeclarationMapper termsDeclarationMapper;

    public List<TermsDeclarationDto> getAllTerms() {
        List<TermsDeclarationDto> activeTerms = termsDeclarationRepository.findAll().stream()
                .filter(term -> "ACTIVE".equalsIgnoreCase(term.getStatus()))
                .map(termsDeclarationMapper::toDto)
                .collect(Collectors.toList());


            if (activeTerms.isEmpty()) {
        throw new ResourceNotFoundException("No active terms declarations found.");
    }
       return activeTerms;

}}
