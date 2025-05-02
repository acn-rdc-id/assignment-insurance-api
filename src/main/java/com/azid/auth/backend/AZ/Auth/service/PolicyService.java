package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.QuotationApplicationMapper;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import com.azid.auth.backend.AZ.Auth.repository.QuotationApplicationRepository;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final QuotationApplicationRepository quotationApplicationRepository;
    private final PolicyMapper policyMapper;
    private final QuotationApplicationMapper quotationApplicationMapper;
    private final PlanService planService;
    private final UserService userService;
    private final CommonUtils commonUtils;

    public PolicyService(
            PolicyRepository policyRepository,
            QuotationApplicationRepository quotationApplicationRepository,
            PolicyMapper policyMapper,
            QuotationApplicationMapper quotationApplicationMapper, UserRepository userRepository, PlanService planService, UserService userService, CommonUtils commonUtils) {

        this.policyRepository = policyRepository;
        this.quotationApplicationRepository = quotationApplicationRepository;
        this.policyMapper = policyMapper;
        this.quotationApplicationMapper = quotationApplicationMapper;
        this.planService = planService;
        this.userService = userService;
        this.commonUtils = commonUtils;
    }

    public List<PolicyResponseDto> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::constructPolicyResponseDto)
                .collect(Collectors.toList());
    }

    public PolicyResponseDto getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + id));
        return constructPolicyResponseDto(policy);
    }

    public PolicyResponseDto constructPolicyResponseDto(Policy policy) {
        PolicyResponseDto policyResponseDto = policyMapper.policyToPolicyResponseDTO(policy);

        Plan plan = policy.getPlan();
        QuotationApplication quotationApplication = policy.getQuotationApplication();
        PlanInfoDto planInfoDto = PlanInfoDto.builder()
                .planName(plan.getPlanName())
                .coverageTerm(plan.getDuration().toString().concat(" years"))
                .sumAssured(plan.getCoverageAmount())
                .premiumAmount(policy.getPremiumAmount())
                .premiumMode(policy.getPremiumMode())
                .referenceNumber(policy.getReferenceNumber())
                .build();
        policyResponseDto.setPlanResponseDto(planInfoDto);
        policyResponseDto.setApplicationResponseDto(quotationApplicationMapper.toResponseDto(quotationApplication));
        return policyResponseDto;
    }


    public QuotationApplication getQuotationApplication(Long id) {
        return quotationApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation Application not found"));
    }

    public QuotationApplicationResponseDto createApplication(QuotationApplicationRequestDto requestDto) {
        Plan plan = planService.getPlan(requestDto.getPlanId());
        User user = userService.getUserByUserId(requestDto.getUserId());

        QuotationApplication application = new QuotationApplication();
        application.setFullName(requestDto.fullName);
        application.setGender(requestDto.gender);
        application.setNationality(requestDto.nationality);
        application.setIdentificationNo(requestDto.identificationNo);
        application.setCountryOfBirth(requestDto.countryOfBirth);
        application.setPhoneNo(requestDto.phoneNo);
        application.setEmail(requestDto.email);
        application.setDateOfBirth(requestDto.dateOfBirth);
        application.setSmoker(requestDto.isSmoker);
        application.setCigarettesNo(requestDto.cigarettesNo);
        application.setOccupation(requestDto.occupation);
        application.setPurposeOfTransaction(requestDto.purposeOfTransaction);

        application.setUser(user);
        application.setPlan(plan);
        application.setApplicationStatus("PENDING");

        QuotationApplication savedApplication = quotationApplicationRepository.save(application);
        QuotationApplicationResponseDto responseDto = quotationApplicationMapper.toResponseDto(savedApplication);
        responseDto.setId(savedApplication.getId());

        log.info("responseDto: " + responseDto);
        return responseDto;
    }

    public Policy createPolicy(QuotationApplication application, Payment payment, PaymentRequestDto requestDto) {
        Policy policy = new Policy();
        policy.setPolicyNo(commonUtils.generateReferenceNumber("POL"));
        policy.setStartDate(new Date());
        policy.setEndDate(new Date(System.currentTimeMillis() + 31536000000L));
        policy.setStatus("ACTIVE");
        policy.setQuotationApplication(application);
        policy.setPlan(application.getPlan());
        policy.setUser(application.getUser());
        policy.setPayment(payment);
        policy.setPremiumAmount(requestDto.getPlanInfo().getPremiumAmount());
        policy.setPremiumMode(requestDto.getPlanInfo().getPremiumMode());
        policy.setReferenceNumber(requestDto.getPlanInfo().getReferenceNumber());
        return policyRepository.save(policy);
    }

    public void updateStatusAndPayment(Long quotationId, String status, Payment payment) {
        QuotationApplication application = getQuotationApplication(quotationId);
        application.setApplicationStatus(status);
        if (payment != null) {
            application.setPayment(payment);
        }
        quotationApplicationRepository.save(application);
    }


}
