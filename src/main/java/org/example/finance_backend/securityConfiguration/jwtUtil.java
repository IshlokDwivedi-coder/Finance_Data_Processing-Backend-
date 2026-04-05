package org.example.finance_backend.securityConfiguration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class jwtUtil {

    // Read the Secret Key and Time limit from application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    // Create an encryption key using the secret key
    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Creates the token
    public String generateToken(String username,String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role","ROLE_"+role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Reads the Token
    public Claims extractAllClaims(String tokens){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(tokens)
                .getBody();
    }
}
