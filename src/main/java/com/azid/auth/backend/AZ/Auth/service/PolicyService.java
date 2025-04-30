package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.QuotationApplicationMapper;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import com.azid.auth.backend.AZ.Auth.repository.QuotationApplicationRepository;
import com.azid.auth.backend.AZ.Auth.repository.UserRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public PolicyDto getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + id));
        return policyMapper.toDto(policy);
    }

    public PolicyService(
            PolicyRepository policyRepository,
            PaymentRepository paymentRepository,
            QuotationApplicationRepository quotationApplicationRepository,
            PolicyMapper policyMapper,
            PaymentMapper paymentMapper,
            QuotationApplicationMapper quotationApplicationMapper, UserRepository userRepository, PlanService planService, UserService userService, CommonUtils commonUtils) {

        this.policyRepository = policyRepository;
        this.quotationApplicationRepository = quotationApplicationRepository;
        this.policyMapper = policyMapper;
        this.quotationApplicationMapper = quotationApplicationMapper;
        this.planService = planService;
        this.userService = userService;
        this.commonUtils = commonUtils;
    }

    public QuotationApplicationResponseDto createQuotationApplication(QuotationApplicationDto dto){

        QuotationApplication quotationApplication = quotationApplicationMapper.toEntity(dto);
        QuotationApplication savedQuote = quotationApplicationRepository.save(quotationApplication);
        QuotationApplicationDto quotationApplicationDto = quotationApplicationMapper.toDto(savedQuote);

        QuotationApplicationResponseDto quotationApplicationResponseDto = new QuotationApplicationResponseDto();
        quotationApplicationResponseDto.setFullName(quotationApplicationDto.getFullName());
        return quotationApplicationResponseDto;
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
        QuotationApplicationDto quotationApplicationDto = quotationApplicationMapper.toDto(savedApplication);
        log.info("quotationApplicationDto: " + quotationApplicationDto);

        QuotationApplicationResponseDto responseDto = new QuotationApplicationResponseDto();
        responseDto.setQuotationId(quotationApplicationDto.getId());

        responseDto.setPlanName(quotationApplicationDto.getPlan().getPlanName());
        responseDto.setBasePremium(quotationApplicationDto.getPlan().getBasePremium());
        responseDto.setDuration(quotationApplicationDto.getPlan().getDuration());
        responseDto.setFullName(quotationApplicationDto.getFullName());
        responseDto.setEmail(quotationApplicationDto.getEmail());
        responseDto.setGender(quotationApplicationDto.getGender());
        responseDto.setApplicationStatus(quotationApplicationDto.getApplicationStatus());

        //QuotationAppResponseDto responseDto = policyMapper.toResponseDTO();
        log.info("responseDto: " + responseDto);
        return responseDto;
    }

    public Policy createPolicy(QuotationApplication application, Payment payment) {
        Policy policy = new Policy();
        policy.setPolicyNo(commonUtils.generatePolicyNumber("POL"));
        policy.setStartDate(new Date());
        policy.setEndDate(new Date(System.currentTimeMillis() + 31536000000L));
        policy.setStatus("ACTIVE");
        policy.setPlan(application.getPlan());
        policy.setUser(application.getUser());
        policy.setPayment(payment);
        policy.setQuotationApplication(application);
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
