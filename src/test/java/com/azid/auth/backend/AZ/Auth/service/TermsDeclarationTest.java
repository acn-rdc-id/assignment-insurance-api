package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.TermsDeclarationDto;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.TermsDeclarationMapper;
import com.azid.auth.backend.AZ.Auth.model.TermsDeclaration;
import com.azid.auth.backend.AZ.Auth.repository.TermsDeclarationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermsDeclarationTest {
    @InjectMocks
    TermsDeclarationService termsDeclarationService;

    @Mock
    TermsDeclarationRepository termsDeclarationRepository;

    @Mock
    TermsDeclarationMapper termsDeclarationMapper;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(termsDeclarationService, "termsDeclarationRepository", termsDeclarationRepository);
        ReflectionTestUtils.setField(termsDeclarationService, "termsDeclarationMapper", termsDeclarationMapper);
    }

    @Test
    void getAllTerm_activeTerm() {
        TermsDeclaration termsDeclaration = new TermsDeclaration();
        termsDeclaration.setId(123L);
        termsDeclaration.setTermsHtml("termsHtml");
        termsDeclaration.setStatus("ACTIVE");
        termsDeclaration.setCreatedAt(LocalDateTime.now());
        termsDeclaration.setUpdatedAt(LocalDateTime.now());
        termsDeclaration.setIsRequired(Boolean.TRUE);

        when(termsDeclarationRepository.findAll()).thenReturn(List.of(termsDeclaration));

        List<TermsDeclarationDto> result = termsDeclarationService.getAllTerms();
        assertNotNull(result);
    }

    @Test
    void getAllTerm_inactiveTerm() {
        TermsDeclaration termsDeclaration = new TermsDeclaration();
        termsDeclaration.setId(123L);
        termsDeclaration.setTermsHtml("termsHtml");
        termsDeclaration.setStatus("INACTIVE");
        termsDeclaration.setCreatedAt(LocalDateTime.now());
        termsDeclaration.setUpdatedAt(LocalDateTime.now());
        termsDeclaration.setIsRequired(Boolean.TRUE);

        when(termsDeclarationRepository.findAll()).thenReturn(List.of(termsDeclaration));

        assertThrows(ResourceNotFoundException.class, () -> termsDeclarationService.getAllTerms());
    }
}
