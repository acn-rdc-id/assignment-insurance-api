package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PaymentDetailsDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PolicyService policyService;
    private final PaymentMapper paymentMapper;
    private final CommonUtils commonUtils;

    public PaymentService(PaymentRepository paymentRepository, PolicyService policyService, PolicyMapper policyMapper, PaymentMapper paymentMapper, CommonUtils commonUtils) {
        this.paymentRepository = paymentRepository;
        this.policyService = policyService;
        this.paymentMapper = paymentMapper;
        this.commonUtils = commonUtils;
    }

    public PaymentResponseDto handlePayment(String status, PaymentRequestDto requestDto) {
        QuotationApplication application = policyService.getQuotationApplication(requestDto.getQuotationId());
        PaymentResponseDto responseDto = new PaymentResponseDto();

        if ("success".equalsIgnoreCase(status)) {
            Payment payment = new Payment();
            payment.setPaymentAmount(requestDto.getPaymentAmount());
            payment.setPaymentDate(new Date());
            payment.setDuration(requestDto.getDuration());
            payment.setPaymentStatus("SUCCESS");
            payment.setQuotationApplication(application);
            payment.setReferenceNumber(commonUtils.generateReferenceNumber("T"));
            payment = paymentRepository.save(payment);

            policyService.updateStatusAndPayment(requestDto.getQuotationId(), "SUCCESS", payment);
            Policy policy = policyService.createPolicy(application, payment, requestDto);
            PolicyResponseDto policyResponseDto = policyService.constructPolicyResponseDto(policy);
            responseDto.setPolicy(policyResponseDto);
            responseDto.setMessage("Payment success, policy created.");

            PaymentDetailsDto paymentDetailsDto = paymentMapper.paymentToPaymentDetailsDto(payment);
            paymentDetailsDto.setPaymentId(payment.getId());
            responseDto.setPaymentDetails(paymentDetailsDto);

        } else {
            policyService.updateStatusAndPayment(requestDto.getQuotationId(), "FAILED", null);
            responseDto.setMessage("Payment failed, application marked as FAILED.");
        }
        return responseDto;
    }

}
