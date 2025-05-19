package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PaymentDetailsDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.model.QuotationApplication;
import com.azid.auth.backend.AZ.Auth.model.enums.PaymentStatus;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    PaymentService paymentService;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    PolicyService policyService;
    @Mock
    PaymentMapper paymentMapper;
    @Mock
    CommonUtils commonUtils;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(paymentService, "paymentRepository", paymentRepository);
        ReflectionTestUtils.setField(paymentService, "policyService", policyService);
        ReflectionTestUtils.setField(paymentService, "paymentMapper", paymentMapper);
        ReflectionTestUtils.setField(paymentService, "commonUtils", commonUtils);
    }

    @Test
    void handlePayment_paymentSuccess() {
        QuotationApplication quotationApplication = new QuotationApplication();
        Policy policy = new Policy();
        PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();

        when(policyService.getQuotationApplication(any())).thenReturn(quotationApplication);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocationOnMock -> {
            Payment payment = invocationOnMock.getArgument(0);
            payment.setId(12345L);
            return payment;
        });
        when(policyService.createPolicy(any(), any())).thenReturn(policy);
        when(paymentMapper.paymentToPaymentDetailsDto(any())).thenReturn(paymentDetailsDto);

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setQuotationId(123L);
        paymentRequestDto.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRequestDto.setPaymentAmount(new BigDecimal(100));

        PaymentResponseDto result = paymentService.handlePayment(paymentRequestDto);
        assertNotNull(result);
    }

    @Test
    void handlePayment_paymentFailed() {
        QuotationApplication quotationApplication = new QuotationApplication();

        when(policyService.getQuotationApplication(any())).thenReturn(quotationApplication);

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setQuotationId(123L);
        paymentRequestDto.setPaymentStatus(PaymentStatus.FAILED);
        paymentRequestDto.setPaymentAmount(new BigDecimal(100));

        PaymentResponseDto result = paymentService.handlePayment(paymentRequestDto);
        assertEquals("Payment failed, application marked as FAILED.", result.getMessage());
    }
}
