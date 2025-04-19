package com.azid.auth.backend.AZ.Auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtils {

    private final String jwtSecret = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY11111111111111111111111111111111111111111asdbasdbadbasbdbasbdbasdbabdbasb22222222222222222222222basdbsabdbasbdabsdb1111111111111bsabdbsadbasbdbasbd"; // Use your Base64 key
    private final int jwtExpirationMs = 86400000; // 24 hours

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Use the Base64 key
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


