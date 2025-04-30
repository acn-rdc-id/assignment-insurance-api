package com.azid.auth.backend.AZ.Auth.controller;

import com.azid.auth.backend.AZ.Auth.dto.PaymentRequestDto;
import com.azid.auth.backend.AZ.Auth.dto.PaymentResponseDto;
import com.azid.auth.backend.AZ.Auth.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/handle-payment/{status}")
    public ResponseEntity<PaymentResponseDto> handlePayment(@PathVariable String status, @RequestBody PaymentRequestDto requestDto) {
        PaymentResponseDto responseDto = paymentService.handlePayment(status, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
