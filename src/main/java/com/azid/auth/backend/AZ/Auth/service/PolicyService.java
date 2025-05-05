package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.PlanMapper;
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
    private final PlanMapper planMapper;
    private final PlanService planService;
    private final UserService userService;
    private final CommonUtils commonUtils;

    public PolicyService(
            PolicyRepository policyRepository,
            QuotationApplicationRepository quotationApplicationRepository,
            PolicyMapper policyMapper,
            QuotationApplicationMapper quotationApplicationMapper, UserRepository userRepository, PlanService planService, UserService userService, CommonUtils commonUtils,
            PlanMapper planMapper) {

        this.policyRepository = policyRepository;
        this.quotationApplicationRepository = quotationApplicationRepository;
        this.policyMapper = policyMapper;
        this.quotationApplicationMapper = quotationApplicationMapper;
        this.planService = planService;
        this.userService = userService;
        this.commonUtils = commonUtils;
        this.planMapper = planMapper;
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

        QuotationApplicationResponseDto applicationResponseDto = quotationApplicationMapper.toResponseDto(quotationApplication);
        applicationResponseDto.setPlanResponseDto(planInfoDto);
        policyResponseDto.setApplicationResponseDto(applicationResponseDto);
        return policyResponseDto;
    }


    public QuotationApplication getQuotationApplication(Long id) {
        log.info("start [getQuotationApplication] applicationId: {}", id);
        return quotationApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation Application not found"));
    }

    public QuotationApplicationResponseDto createApplication(QuotationApplicationRequestDto requestDto) {
        PersonDto personDto = requestDto.getPersonDto();
        PlanInfoDto planInfoDto = requestDto.getPlanInfoDto();
        QuotationApplicationResponseDto responseDto = new QuotationApplicationResponseDto();

        if (Objects.nonNull(personDto) && Objects.nonNull(planInfoDto)) {
            Plan plan = planService.getPlan(planInfoDto.getId());
            User user = userService.getUserByUserId(personDto.getUserId());

            QuotationApplication application = new QuotationApplication();
            // personDto
            application.setFullName(personDto.getFullName());
            application.setGender(personDto.getGender());
            application.setNationality(personDto.getNationality());
            application.setIdentificationNo(personDto.getIdentificationNo());
            application.setOtherId(personDto.getOtherId());//new add
            application.setCountryOfBirth(personDto.getCountryOfBirth());
            application.setAge(personDto.getAge()); //new add
            application.setTitle(personDto.getTitle()); // new add
            application.setCountryCode(personDto.getCountryCode());
            application.setPhoneNo(personDto.getPhoneNo());
            application.setEmail(personDto.getEmail());
            application.setDateOfBirth(personDto.getDateOfBirth());
            application.setSmoker(personDto.isSmoker());
            application.setUsPerson(personDto.isUsPerson()); //new add
            application.setCigarettesNo(personDto.getCigarettesNo());
            application.setOccupation(personDto.getOccupation());
            application.setPurposeOfTransaction(personDto.getPurposeOfTransaction());

            //planInfoDto
            application.setPremiumAmount(planInfoDto.getPremiumAmount());
            application.setPremiumMode(planInfoDto.getPremiumMode());
            application.setReferenceNumber(planInfoDto.getReferenceNumber());

            application.setUser(user);
            application.setPlan(plan);
            application.setApplicationStatus("PENDING");

            QuotationApplication savedApplication = quotationApplicationRepository.save(application);
            responseDto =  quotationApplicationMapper.toResponseDto(savedApplication);
            responseDto.setPlanResponseDto(requestDto.getPlanInfoDto());
            responseDto.setId(savedApplication.getId());
        }



        log.info("responseDto: " + responseDto);
        return responseDto;
    }

    public Policy createPolicy(QuotationApplication application, Payment payment) {
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

    public void updateStatusAndPayment(Long quotationId, String status, Payment payment) {
        QuotationApplication application = getQuotationApplication(quotationId);
        application.setApplicationStatus(status);
        if (payment != null) {
            application.setPayment(payment);
        }
        quotationApplicationRepository.save(application);
    }


}
