package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.*;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.QuotationApplicationMapper;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import com.azid.auth.backend.AZ.Auth.repository.QuotationApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PaymentRepository paymentRepository;
    private final QuotationApplicationRepository quotationApplicationRepository;
    private final PolicyMapper policyMapper;
    private final PaymentMapper paymentMapper;
    private final QuotationApplicationMapper quotationApplicationMapper;

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
            QuotationApplicationMapper quotationApplicationMapper) {

        this.policyRepository = policyRepository;
        this.paymentRepository = paymentRepository;
        this.quotationApplicationRepository = quotationApplicationRepository;
        this.policyMapper = policyMapper;
        this.paymentMapper = paymentMapper;
        this.quotationApplicationMapper = quotationApplicationMapper;
    }

    public QuotationApplicationResponseDto createPolicy(QuotationApplicationDto dto){


            //fe hantar status success/fail
            //create quotationApplication
            // create payment

           //kalau fe hantar status success
           //create policy
        // update psyment success

           //kalau fe hantar status failed
           //return response status failed
           //so we wont create policy
        // updfate payment failed

        QuotationApplication quotationApplication = quotationApplicationMapper.toEntity(dto);
        QuotationApplication savedQuote = quotationApplicationRepository.save(quotationApplication);
        QuotationApplicationDto quotationApplicationDto = quotationApplicationMapper.toDto(savedQuote);

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentAmount(dto.getPaymentAmount());
        paymentDto.setPaymentDate(new Date());
        paymentDto.setPaymentStatus(dto.getPaymentStatus());
        paymentDto.setDuration(dto.getPaymentDuration());
        Payment payment =  paymentMapper.toEntity(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        PaymentDto paymentResponseDto = paymentMapper.toDto(savedPayment);

        QuotationApplicationResponseDto quotationApplicationResponseDto = new QuotationApplicationResponseDto();
        quotationApplicationResponseDto.setPaymentStatus(paymentResponseDto.getPaymentStatus());
        quotationApplicationResponseDto.setFullName(quotationApplicationDto.getFullName());
        return quotationApplicationResponseDto;
    }

}
