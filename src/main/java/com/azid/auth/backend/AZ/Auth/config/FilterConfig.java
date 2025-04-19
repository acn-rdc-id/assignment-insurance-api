package com.azid.auth.backend.AZ.Auth.config;

import com.azid.auth.backend.AZ.Auth.security.JwtAuthorizationFilter;
import com.azid.auth.backend.AZ.Auth.utils.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final JwtUtils jwtUtils;

    public FilterConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtils);
    }
}
