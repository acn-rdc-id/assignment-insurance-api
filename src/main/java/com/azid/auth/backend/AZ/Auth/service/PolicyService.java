package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.PaymentDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.QuotationApplicationDto;
import com.azid.auth.backend.AZ.Auth.mapper.PaymentMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.model.Payment;
import com.azid.auth.backend.AZ.Auth.model.Policy;
import com.azid.auth.backend.AZ.Auth.repository.PaymentRepository;
import com.azid.auth.backend.AZ.Auth.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PolicyService {

    private static PolicyRepository policyRepository;
    private static PaymentRepository paymentRepository;
    @Autowired
    private static PolicyMapper policyMapper;
    @Autowired
    private static PaymentMapper paymentMapper;

    //public PolicyResponseDto createPolicy(QuotationApplicationDto dto){


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



      /*  PaymentDto paymentDto = dto.getPaymentDto();
        Payment payment =  paymentMapper.toEntity(paymentDto);
        paymentRepository.save(payment);*/





          // return ;
   // }

}
