package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.exceptions.BadRequestException;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
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
import java.util.Objects;
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
            QuotationApplicationMapper quotationApplicationMapper, UserRepository userRepository,
            PlanService planService, UserService userService, CommonUtils commonUtils) {

        this.policyRepository = policyRepository;
        this.quotationApplicationRepository = quotationApplicationRepository;
        this.policyMapper = policyMapper;
        this.quotationApplicationMapper = quotationApplicationMapper;
        this.planService = planService;
        this.userService = userService;
        this.commonUtils = commonUtils;
    }

    public List<PolicyResponseDto> getAllPolicies(String userId) {
        return policyRepository.findByUserId(userId)
                .stream()
                .map(this::constructPolicyResponseDto)
                .collect(Collectors.toList());
    }

    public PolicyResponseDto getPolicyById(Long id) {
        log.info("start [getPolicyById] policyId: {}", id);
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + id));
        return constructPolicyResponseDto(policy);
    }

    public PolicyResponseDto constructPolicyResponseDto(Policy policy) {
        log.info("start [constructPolicyResponseDto] policyId: {}", policy.getId());
        PolicyResponseDto policyResponseDto = policyMapper.policyToPolicyResponseDTO(policy);

        Plan plan = policy.getPlan();
        QuotationApplication quotationApplication = policy.getQuotationApplication();
        PlanInfoDto planInfoDto = PlanInfoDto.builder()
                .id(plan.getId())
                .planName(plan.getPlanName())
                .coverageTerm(plan.getDuration().toString().concat(" years"))
                .sumAssured(plan.getCoverageAmount())
                .premiumAmount(quotationApplication.getPremiumAmount())
                .premiumMode(quotationApplication.getPremiumMode())
                .referenceNumber(quotationApplication.getReferenceNumber())
                .build();
        log.info("[constructPolicyResponseDto] planInfoDto: {}", planInfoDto);

        QuotationApplicationResponseDto applicationResponseDto = quotationApplicationMapper.toResponseDto(quotationApplication);
        log.info("[constructPolicyResponseDto] applicationResponseDto: {}", applicationResponseDto);
        applicationResponseDto.setPlanResponseDto(planInfoDto);
        policyResponseDto.setApplicationResponseDto(applicationResponseDto);
        return policyResponseDto;
    }


    public QuotationApplication getQuotationApplication(Long id) {
        log.info("start [getQuotationApplication] applicationId: {}", id);
        return quotationApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation Application not found"));
    }

    public QuotationApplicationResponseDto createApplication(QuotationApplicationRequestDto requestDto,String userId) {
        PersonDto personDto = requestDto.getPersonDto();
        PlanInfoDto planInfoDto = requestDto.getPlanInfoDto();

        log.info("start [createApplication] for user ID: {}, plan ID: {}",
                Objects.nonNull(personDto) ? userId : null,
                Objects.nonNull(planInfoDto) ? planInfoDto.getId() : null);

        try {
            log.info("[createApplication] Fetching plan ID: {}", planInfoDto.getId());
            Plan plan = planService.getPlan(planInfoDto.getId());

            log.info("[createApplication] Fetching user ID: {}", userId);
            User user = userService.getUserByUserId(userId);

            QuotationApplication application = buildApplicationFromDto(personDto, planInfoDto);
            application.setUser(user);
            application.setPlan(plan);
            application.setApplicationStatus("PENDING");
            QuotationApplication savedApplication = quotationApplicationRepository.save(application);
            log.info("[createApplication] Application saved successfully ID: {}", savedApplication.getId());

            QuotationApplicationResponseDto responseDto = quotationApplicationMapper.toResponseDto(savedApplication);
            responseDto.setPlanResponseDto(planInfoDto);
            responseDto.setId(savedApplication.getId());

            log.info("[createApplication] Application creation complete for user ID: {}", userId);
            return responseDto;

        } catch (BadRequestException | ResourceNotFoundException e) {
            log.warn("[createApplication] Known exception: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[createApplication] Unexpected error while creating application", e);
            throw new RuntimeException("An unexpected error occurred. Please try again later.");
        }
    }

    private QuotationApplication buildApplicationFromDto(PersonDto personDto, PlanInfoDto planInfoDto) {
        QuotationApplication application = new QuotationApplication();

        application.setFullName(personDto.getFullName());
        application.setGender(personDto.getGender());
        application.setNationality(personDto.getNationality());
        application.setIdentificationNo(personDto.getIdentificationNo());
        application.setOtherId(personDto.getOtherId());
        application.setCountryOfBirth(personDto.getCountryOfBirth());
        application.setAge(personDto.getAge());
        application.setTitle(personDto.getTitle());
        application.setCountryCode(personDto.getCountryCode());
        application.setPhoneNo(personDto.getPhoneNo());
        application.setEmail(personDto.getEmail());
        application.setDateOfBirth(personDto.getDateOfBirth());
        application.setSmoker(Boolean.TRUE.equals(personDto.getIsSmoker()));
        application.setUsPerson(Boolean.TRUE.equals(personDto.getIsUsPerson()));
        application.setCigarettesNo(personDto.getCigarettesNo());
        application.setOccupation(personDto.getOccupation());
        application.setPurposeOfTransaction(personDto.getPurposeOfTransaction());

        application.setPremiumAmount(planInfoDto.getPremiumAmount());
        application.setPremiumMode(planInfoDto.getPremiumMode());
        application.setReferenceNumber(planInfoDto.getReferenceNumber());

        return application;
    }

    public Policy createPolicy(QuotationApplication application, Payment payment) {
        log.info("[createPolicy] application ID: {}, payment ID: {}", application.getId(), payment.getId());
        Policy policy = new Policy();
        policy.setPolicyNo(commonUtils.generateReferenceNumber("POL"));
        policy.setStartDate(new Date());
        policy.setEndDate(new Date(System.currentTimeMillis() + 31536000000L));
        policy.setStatus("ACTIVE");
        policy.setQuotationApplication(application);
        policy.setPlan(application.getPlan());
        policy.setUser(application.getUser());
        policy.setPayment(payment);
        return policyRepository.save(policy);
    }

    public void updateStatusAndPayment(Long applicationId, String status, Payment payment) {
        log.info("[updateStatusAndPayment] application ID: {}, payment ID: {}", applicationId, payment.getId());
        QuotationApplication application = getQuotationApplication(applicationId);
        application.setApplicationStatus(status);
        if (Objects.nonNull(payment)) {
            application.setPayment(payment);
        }
        quotationApplicationRepository.save(application);
    }


}
