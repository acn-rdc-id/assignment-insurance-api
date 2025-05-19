package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimListResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClaimServiceTest {
    @InjectMocks
    ClaimService claimService;

    @Mock
    ClaimRepository claimRepository;

    @Mock
    ClaimTypeRepository claimTypeRepository;

    @Mock
    ClaimDocumentRepository claimDocumentRepository;

    @Mock
    DocumentTypeRepository documentTypeRepository;

    @Mock
    PolicyRepository policyRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Mock
    AwsS3Service awsS3Service;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(claimService, "claimRepository", claimRepository);
        ReflectionTestUtils.setField(claimService, "claimTypeRepository", claimTypeRepository);
        ReflectionTestUtils.setField(claimService, "claimDocumentRepository", claimDocumentRepository);
        ReflectionTestUtils.setField(claimService, "documentTypeRepository", documentTypeRepository);
        ReflectionTestUtils.setField(claimService, "policyRepository", policyRepository);
        ReflectionTestUtils.setField(claimService, "userRepository", userRepository);
        ReflectionTestUtils.setField(claimService, "userService", userService);
        ReflectionTestUtils.setField(claimService, "awsS3Service", awsS3Service);
    }

    @Test
    void getAllClaim_validResponse() {
        Claim claim = new Claim();
        claim.setClaimId(123L);

        ClaimType claimType = new ClaimType();
        claimType.setClaimTypeId(123L);
        claimType.setClaimTypeName("claimTypeName");
        claimType.setClaimTypeDescription("claimTypeDesc");

        claim.setClaimType(claimType);

        when(claimRepository.findByUserUserId(any())).thenReturn(List.of(claim));
        when(claimTypeRepository.getClaimTypeByClaimId(any())).thenReturn(claimType);

        List<ClaimListResponseDto> result = claimService.getAllClaims("userId");
        assertFalse(result.isEmpty());
    }

    @Test
    void getClaimDetailsByClaimId_validResponse() {
        ClaimType claimType = new ClaimType();
        claimType.setClaimTypeId(123L);
        claimType.setClaimTypeName("claimTypeName");
        claimType.setClaimTypeDescription("claimTypeDesc");

        Policy policy = new Policy();
        policy.setPolicyNo("123");

        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setClaim_date(LocalDate.now());
        claim.setClaimStatus("claimStatus");

        DocumentType documentType = new DocumentType();
        documentType.setDocumentTypeName("documentTypeName");

        ClaimDocument claimDocument = new ClaimDocument();
        claimDocument.setDocumentType(documentType);
        claimDocument.setDocumentUrl("documentUrl");

        when(claimTypeRepository.getClaimTypeByClaimId(any())).thenReturn(claimType);
        when(claimRepository.findById(any())).thenReturn(Optional.of(claim));
        when(claimDocumentRepository.getClaimDocumentByClaimId(any())).thenReturn(List.of(claimDocument));

        ClaimResponseDto result = claimService.getClaimDetailsByClaimId(123L,"userId");
        assertNotNull(result);
    }

    @Test
    void submitClaim_validResponse() {
        ClaimType claimType = new ClaimType();
        claimType.setClaimTypeId(123L);
        claimType.setClaimTypeName("claimTypeName");
        claimType.setClaimTypeDescription("claimTypeDesc");

        DocumentType documentType = new DocumentType();
        documentType.setDocumentTypeId(123L);
        documentType.setClaimType(claimType);
        documentType.setDocumentTypeName("documentTypeName");
        documentType.setRequired(Boolean.FALSE);

        User user = new User();

        Policy policy = new Policy();
        policy.setPolicyNo("123");

        when(documentTypeRepository.findByClaimTypeId(any())).thenReturn(List.of(documentType));
        when(userService.getUserByUserId(any())).thenReturn(user);
        when(claimTypeRepository.findById(any())).thenReturn(Optional.of(claimType));
        when(policyRepository.findById(any())).thenReturn(Optional.of(policy));

        doNothing().when(awsS3Service).uploadFileToS3(anyLong(), anyString(), anyString(), anyList());
        when(claimRepository.save(any(Claim.class))).thenAnswer(invocationOnMock -> {
            Claim claim = invocationOnMock.getArgument(0);
            claim.setClaimId(123L);
            claim.setClaimStatus("claimStatus");
            return claim;
        });

        MockMultipartFile document = new MockMultipartFile(
                "files",
                "test1.txt",
                "text/plain",
                "Hello World".getBytes()
        );

        ClaimResponseDto result = claimService.submitClaim("123", "123", "123", List.of(document));
        assertNotNull(result);
    }

    @Test
    void getClaimInfoByUserId_validResponse() {
        User user = new User();
        user.setUsername("username");

        Policy policy = new Policy();
        policy.setId(123L);
        policy.setUser(user);
        policy.setPolicyNo("123");

        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(policyRepository.findByUserId(any())).thenReturn(List.of(policy));
        when(claimTypeRepository.getClaimTypesWithDocuments()).thenReturn(List.of());

        ClaimInfoResponse result = claimService.getClaimInfoByUserId("123");
        assertNotNull(result);
    }
}
