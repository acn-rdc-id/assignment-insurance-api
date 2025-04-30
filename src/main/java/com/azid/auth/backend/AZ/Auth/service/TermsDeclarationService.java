package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.mapper.TermsDeclarationMapper;
import com.azid.auth.backend.AZ.Auth.repository.TermsDeclarationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermsDeclarationService {

    private  final TermsDeclarationRepository termsDeclarationRepository;
    private final TermsDeclarationMapper termsDeclarationMapper;

    public List<TermsDeclarationDto>getAllTerms(){
        return termsDeclarationRepository.findAll().stream().map(termsDeclarationMapper::toDto).collect(Collectors.toList());
    }

}
