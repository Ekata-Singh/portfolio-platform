package com.ektasingh.portfolio.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private static final String DEV_SECRET = "local-dev-secret-key-do-not-use-in-production";

    private final Environment environment;

    public JwtService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Fail fast on an unsafe signing key: the HMAC key must be at least 256 bits,
     * and the public development default must never be used in production.
     */
    @PostConstruct
    void validateSecret() {
        if (secretKey == null || secretKey.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("JWT_SECRET must be at least 32 characters long.");
        }
        if (DEV_SECRET.equals(secretKey) && environment.acceptsProfiles(Profiles.of("prod"))) {
            throw new IllegalStateException(
                    "JWT_SECRET is not set. Set it to a long random value in production.");
        }
    }

    /**
     * Generate JWT with custom claims
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Generate JWT without extra claims
     */
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Extract username from token
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generic claim extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Validate token
     */
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Check expiration
     */
    private boolean isTokenExpired(String token) {

        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }

    /**
     * Parse all claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Create signing key
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8));
    }
}