package com.example.notes.configuration;

import com.example.notes.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenGenerator {

    private final byte[] secretKey;

    public JwtTokenGenerator(@Value("${jwt.secret}") String secret) {
        // Decode the Base64-encoded key
        this.secretKey = Base64.getDecoder().decode(secret);
    }

    public String generateToken(String username) {
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + 70000000);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Token has expired", e);
        } catch (JwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token", e);
        }
    }
}