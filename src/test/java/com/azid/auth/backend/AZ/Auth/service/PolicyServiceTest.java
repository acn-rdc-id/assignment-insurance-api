package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.BeneficiaryMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.QuotationApplicationMapper;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.BeneficiaryRepository;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import com.azid.auth.backend.AZ.Auth.repository.QuotationApplicationRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {
    @InjectMocks
    PolicyService policyService;

    @Mock
    PolicyRepository policyRepository;

    @Mock
    QuotationApplicationRepository quotationApplicationRepository;

    @Mock
    PolicyMapper policyMapper;

    @Mock
    QuotationApplicationMapper quotationApplicationMapper;

    @Mock
    BeneficiaryMapper beneficiaryMapper;

    @Mock
    BeneficiaryRepository beneficiaryRepository;

    @Mock
    PlanService planService;

    @Mock
    UserService userService;

    @Mock
    CommonUtils commonUtils;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(policyService, "policyRepository", policyRepository);
        ReflectionTestUtils.setField(policyService, "quotationApplicationRepository", quotationApplicationRepository);
        ReflectionTestUtils.setField(policyService, "policyMapper", policyMapper);
        ReflectionTestUtils.setField(policyService, "quotationApplicationMapper", quotationApplicationMapper);
        ReflectionTestUtils.setField(policyService, "beneficiaryMapper", beneficiaryMapper);
        ReflectionTestUtils.setField(policyService, "beneficiaryRepository", beneficiaryRepository);
        ReflectionTestUtils.setField(policyService, "planService", planService);
        ReflectionTestUtils.setField(policyService, "userService", userService);
        ReflectionTestUtils.setField(policyService, "commonUtils", commonUtils);
    }

    @Test
    void getAllPolicies_validResponse() {
        Policy policy = mockPolicy();
        PolicyResponseDto policyResponseDto = mockPolicyResponseDto();

        when(policyRepository.findByUserId(any())).thenReturn(List.of(policy));
        when(policyMapper.policyToPolicyResponseDTO(any())).thenReturn(policyResponseDto);
        when(quotationApplicationMapper.toResponseDto(any())).thenReturn(new QuotationApplicationResponseDto());

        List<PolicyResponseDto> result = policyService.getAllPolicies("123");
        assertNotNull(result);
    }

    @Test
    void getPolicyById_validResponse() {
        Policy policy = mockPolicy();
        PolicyResponseDto policyResponseDto = mockPolicyResponseDto();
        Beneficiary beneficiary = mockBeneficiary();

        when(policyRepository.findById(any())).thenReturn(Optional.of(policy));
        when(policyMapper.policyToPolicyResponseDTO(any())).thenReturn(policyResponseDto);
        when(beneficiaryRepository.findByPolicyId(any())).thenReturn(List.of(beneficiary));
        when(quotationApplicationMapper.toResponseDto(any())).thenReturn(new QuotationApplicationResponseDto());

        PolicyResponseDto response = policyService.getPolicyById(123L);
        assertNotNull(response);
    }

    @Test
    void getQuotationApplication_validResponse() {
        QuotationApplication quotationApplication = mockQuotationApplication();

        when(quotationApplicationRepository.findById(any())).thenReturn(Optional.of(quotationApplication));

        QuotationApplication result = policyService.getQuotationApplication(100L);
        assertNotNull(result);
    }

    @Test
    void createApplication_validResponse() {
        Plan plan = mockPlan();
        User user = mockUser();

        when(planService.getPlan(any())).thenReturn(plan);
        when(userService.getUserByUserId(any())).thenReturn(user);
        when(quotationApplicationRepository.save(any(QuotationApplication.class))).thenAnswer(invocationOnMock -> {
            QuotationApplication quotationApplication = invocationOnMock.getArgument(0);
            quotationApplication.setId(123L);
            quotationApplication.setGender("Male");
            return quotationApplication;
        });
        when(quotationApplicationMapper.toResponseDto(any())).thenReturn(new QuotationApplicationResponseDto());

        PersonDto personDto = new PersonDto();
        personDto.setGender("Male");
        personDto.setAge(20);
        personDto.setTitle("title");
        personDto.setFullName("fullName");
        personDto.setNationality("nationality");
        personDto.setIdentificationNo("123");
        personDto.setOtherId("123");
        personDto.setIsUsPerson(Boolean.FALSE);
        personDto.setEmail("johndoe@test.com");
        personDto.setDateOfBirth(new Date());
        personDto.setSmoker(Boolean.FALSE);
        personDto.setCountryOfBirth("country");
        personDto.setCountryCode("C");
        personDto.setCigarettesNo(1);
        personDto.setPhoneNo("123");
        personDto.setOccupation("occupation");
        personDto.setPurposeOfTransaction("transactionPurpose");

        PlanInfoDto planInfoDto = new PlanInfoDto();
        planInfoDto.setId(123L);
        planInfoDto.setPlanName("planName");
        planInfoDto.setSumAssured(100.0);
        planInfoDto.setCoverageTerm("coverageTerm");
        planInfoDto.setPremiumAmount(BigDecimal.valueOf(100));
        planInfoDto.setPremiumMode("premiumMode");
        planInfoDto.setReferenceNumber("referenceNumber");

        QuotationApplicationRequestDto quotationApplicationRequestDto = new QuotationApplicationRequestDto();
        quotationApplicationRequestDto.setPersonDto(personDto);
        quotationApplicationRequestDto.setPlanInfoDto(planInfoDto);

        QuotationApplicationResponseDto result = policyService.createApplication(quotationApplicationRequestDto, "123");
        assertNotNull(result);
    }

    @Test
    void createPolicy_validResponse() {
        QuotationApplication quotationApplication = mockQuotationApplication();
        Payment payment = mockPayment();

        policyService.createPolicy(quotationApplication, payment);
        verify(policyRepository, times(1)).save(any());
    }

    @Test
    void updateStatusAndPayment_validResponse() {
        Payment payment = mockPayment();
        QuotationApplication quotationApplication = mockQuotationApplication();

        when(quotationApplicationRepository.findById(any())).thenReturn(Optional.of(quotationApplication));

        policyService.updateStatusAndPayment(123L, "ACTIVE", payment);
        verify(quotationApplicationRepository, times(1)).save(any());
    }

    @Test
    void updatePolicy_validResponse() {
        Policy policy = mockPolicy();

        when(policyRepository.findById(any())).thenReturn(Optional.of(policy));
        when(quotationApplicationRepository.save(any(QuotationApplication.class))).thenAnswer(invocationOnMock -> {
            QuotationApplication quotationApplication = invocationOnMock.getArgument(0);
            quotationApplication.setId(123L);
            quotationApplication.setGender("Male");
            return quotationApplication;
        });
        when(quotationApplicationMapper.toResponseDto(any())).thenReturn(new QuotationApplicationResponseDto());

        PolicyServicingDto policyServicingDto = new PolicyServicingDto();
        policyServicingDto.setTitle("title");
        policyServicingDto.setFullName("fullName");
        policyServicingDto.setCountryCode("countryCode");
        policyServicingDto.setPhoneNo("123");
        policyServicingDto.setEmail("johndoe@test.com");

        QuotationApplicationResponseDto result = policyService.updatePolicy(123L, policyServicingDto);
        assertNotNull(result);
    }

    @Test
    void upsertAll_validResponse() {
        Beneficiary beneficiary = mockBeneficiary();
        Policy policy = mockPolicy();

        when(beneficiaryRepository.findByPolicy(any())).thenReturn(List.of(beneficiary));
        when(policyRepository.findByUserId(any())).thenReturn(List.of(policy));
        when(beneficiaryMapper.toEntity(any())).thenReturn(beneficiary);

        BeneficiaryDto beneficiaryDto = mockBeneficiaryDto();

        BeneficiaryRequestDto beneficiaryRequestDto = new BeneficiaryRequestDto();
        beneficiaryRequestDto.setPolicyNo("123");
        beneficiaryRequestDto.setBeneficiaries(List.of(beneficiaryDto));

        BeneficiaryResponseDto result = policyService.upsertAll(beneficiaryRequestDto, "123");
        assertNotNull(result);
    }

    private QuotationApplication mockQuotationApplication() {
        QuotationApplication quotationApplication = new QuotationApplication();
        quotationApplication.setId(123L);
        quotationApplication.setGender("Male");

        return quotationApplication;
    }

    private Plan mockPlan() {
        Plan plan = new Plan();
        plan.setId(123L);
        plan.setPlanName("planName");
        plan.setCoverageAmount(10.0);
        plan.setBasePremium(BigDecimal.valueOf(10));
        plan.setDuration(10);
        plan.setStatus("ACTIVE");

        return plan;
    }

    private User mockUser() {
        User user = new User();
        user.setId(123L);
        user.setUserId("123");
        user.setUsername("userName");
        user.setEmail("user@email.com");

        return user;
    }

    private Policy mockPolicy() {
        Plan plan = mockPlan();
        User user = mockUser();

        QuotationApplication quotationApplication = mockQuotationApplication();

        Payment payment = mockPayment();

        Policy policy = new Policy();
        policy.setId(123L);
        policy.setPolicyNo("123");
        policy.setStartDate(new Date());
        policy.setEndDate(new Date());
        policy.setStatus("ACTIVE");
        policy.setPlan(plan);
        policy.setUser(user);
        policy.setPayment(payment);
        policy.setQuotationApplication(quotationApplication);

        return policy;
    }

    private BeneficiaryDto mockBeneficiaryDto() {
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();
        beneficiaryDto.setId(123L);
        beneficiaryDto.setBeneficiaryName("beneficiaryName");
        beneficiaryDto.setRelationshipToInsured("relationshipToInsured");
        beneficiaryDto.setShare(50F);
        beneficiaryDto.setAction(BeneficiaryDto.Action.CREATE);

        return beneficiaryDto;
    }

    private PolicyResponseDto mockPolicyResponseDto() {
        BeneficiaryDto beneficiaryDto = mockBeneficiaryDto();

        PolicyResponseDto policyResponseDto = new PolicyResponseDto();
        policyResponseDto.setId(123L);
        policyResponseDto.setPolicyNo("12345");
        policyResponseDto.setStartDate(new Date());
        policyResponseDto.setEndDate(new Date());
        policyResponseDto.setApplicationResponseDto(new QuotationApplicationResponseDto());
        policyResponseDto.setStatus("ACTIVE");
        policyResponseDto.setBeneficiaryList(List.of(beneficiaryDto));

        return policyResponseDto;
    }

    private Beneficiary mockBeneficiary() {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(123L);
        beneficiary.setBeneficiaryName("beneficiaryName");
        beneficiary.setRelationshipToInsured("relationshipToInsured");
        beneficiary.setShare(50F);

        return beneficiary;
    }

    private Payment mockPayment() {
        QuotationApplication quotationApplication = mockQuotationApplication();

        Payment payment = new Payment();
        payment.setId(123L);
        payment.setPaymentDate(new Date());
        payment.setPaymentAmount(BigDecimal.valueOf(100));
        payment.setPaymentStatus("SUCCESS");
        payment.setDuration(1);
        payment.setReferenceNumber("123");
        payment.setQuotationApplication(quotationApplication);

        return payment;
    }
}
