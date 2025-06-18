package com.example.spring_auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;

import java.security.Key;
import java.util.Base64;
import java.util.function.Function;


@Builder
public class JwtService {

    // A long secret key, must be Base64-encoded and at least 256 bits for HS256
    public static final String SECRET = "sdhq9d3021j3e09j0sap9jcdsaic012u302j9dnasidhq9w9";

    // ✅ Extracts the username (which is stored as the 'subject' in the token)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extracts any claim using a resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Parses the entire claims body from token using the signing key
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Generates the signing key from the SECRET string
    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(Base64.getEncoder().encodeToString(SECRET.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
