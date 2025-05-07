package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PaymentDetailsDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.exceptions.BadRequestException;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import com.azid.auth.backend.AZ.Auth.model.enums.PaymentStatus;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PolicyService policyService;
    private final PaymentMapper paymentMapper;
    private final CommonUtils commonUtils;

    public PaymentService(PaymentRepository paymentRepository, PolicyService policyService, PaymentMapper paymentMapper, CommonUtils commonUtils) {
        this.paymentRepository = paymentRepository;
        this.policyService = policyService;
        this.paymentMapper = paymentMapper;
        this.commonUtils = commonUtils;
    }

    public PaymentResponseDto handlePayment(PaymentRequestDto requestDto) {
        log.info("Start [handlePayment] for quotation ID: {}", requestDto.getQuotationId());

        try {
            QuotationApplication application = policyService.getQuotationApplication(requestDto.getQuotationId());
            if (application == null) {
                log.warn("[handlePayment] No QuotationApplication found for ID: {}", requestDto.getQuotationId());
                throw new ResourceNotFoundException("Quotation application not found for ID: " + requestDto.getQuotationId());
            }

            PaymentResponseDto responseDto = new PaymentResponseDto();

            if (PaymentStatus.SUCCESS.equals(requestDto.getPaymentStatus())) {
                log.info("[handlePayment] Processing successful payment for quotationId: {}", requestDto.getQuotationId());
                Payment payment = new Payment();
                payment.setPaymentAmount(requestDto.getPaymentAmount());
                payment.setPaymentDate(new Date());
                payment.setPaymentStatus("SUCCESS");
                payment.setQuotationApplication(application);
                payment.setReferenceNumber(commonUtils.generateReferenceNumber("T"));

                payment = paymentRepository.save(payment);
                log.info("[handlePayment] Payment saved successfully with ID: {}", payment.getId());

                policyService.updateStatusAndPayment(requestDto.getQuotationId(), "SUCCESS", payment);
                log.info("[handlePayment] Quotation application status updated to SUCCESS for quotationId: {}", requestDto.getQuotationId());

                Policy policy = policyService.createPolicy(application, payment);
                PolicyResponseDto policyResponseDto = policyService.constructPolicyResponseDto(policy);

                PaymentDetailsDto paymentDetailsDto = paymentMapper.paymentToPaymentDetailsDto(payment);
                paymentDetailsDto.setPaymentId(payment.getId());

                responseDto.setPolicy(policyResponseDto);
                responseDto.setPaymentDetails(paymentDetailsDto);
                responseDto.setMessage("Payment success, policy created.");

                log.info("[handlePayment] Policy created successfully for quotationId: {}", requestDto.getQuotationId());

            } else {
                log.warn("[handlePayment] Payment failed for quotationId: {}", requestDto.getQuotationId());
                policyService.updateStatusAndPayment(requestDto.getQuotationId(), "FAILED", null);
                responseDto.setMessage("Payment failed, application marked as FAILED.");
            }
            return responseDto;

        } catch (ResourceNotFoundException | BadRequestException e) {
            log.warn("[handlePayment] Known exception: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[handlePayment] Unexpected error for quotationId: {}", requestDto.getQuotationId(), e);
            throw new RuntimeException("Unexpected error occurred while processing payment.");
        }
    }

}
