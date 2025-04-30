package com.azid.auth.backend.AZ.Auth.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public String generatePolicyNumber(String code) {
        return code + System.currentTimeMillis();
    }
}
