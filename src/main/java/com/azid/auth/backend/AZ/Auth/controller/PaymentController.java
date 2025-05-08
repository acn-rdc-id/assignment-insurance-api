package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PaymentRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/handle-payment")
    public ResponseEntity<PaymentResponseDto> handlePayment(@Valid @RequestBody PaymentRequestDto requestDto) {

        log.info("PaymentController: handlePayment STARTED");
        PaymentResponseDto responseDto = paymentService.handlePayment(requestDto);
        log.info("PaymentController: handlePayment ENDED");
        return ResponseEntity.ok(responseDto);

    }
}
