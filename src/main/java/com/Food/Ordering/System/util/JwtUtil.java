package com.Food.Ordering.System.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // Generate token with multiple roles
    public String generateToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)  // Add roles as a list in the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extract roles from the token
    public List extractRoles(String token) {
        return getClaims(token).get("roles", List.class); // Extract roles as List
    }

    // Validate token expiration
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Get claims from the token
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()//Creates a parser to decode the JWT.
                .setSigningKey(key).build()// Uses the secret key to verify the token’s signature (ensures it’s not tampered with).
                .parseClaimsJws(token)// Parses the JWT token and extracts the claims (data inside the token).
                .getBody();//Retrieves the body (payload) of the token where claims (username, role, expiration) are stored.
        return claimsResolver.apply(claims);
    }
}
