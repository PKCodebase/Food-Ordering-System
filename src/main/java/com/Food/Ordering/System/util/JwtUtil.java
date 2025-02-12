package com.Food.Ordering.System.util;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    //JWT (JSON Web Token) is used for stateless authentication, meaning the server does not store session data.
    // Instead, it issues a token to the user, which is passed with every request for authentication.JWT (JSON Web Token) is used for stateless authentication, meaning the server does not store session data.
    // Instead, it issues a token to the user,
    // which is passed with every request for authentication.


    //    🔹 Purpose:
//
//    This secret key is used to sign and verify JWT tokens.
//    It ensures that only the server that created the token can validate it.
//    If someone tries to tamper with the token, verification will fail.
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Sign the token with the secret key
                .compact();  // Convert it into a compact string format
    }


    //    Extracts the username from the JWT token.
//    Calls extractClaim() to get the "subject" field (which stores the username).
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    //Extracts the role from the JWT token.
    //Calls extractClaim() to get the "role" field.
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    //Extracts the username from the token.
    //Compares it with the provided username.
    //Checks if the token is expired.
    //If both conditions are true, the token is valid.
    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }
    //Gets the expiration date of the token.
//Checks if it has already expired.
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()//Creates a parser to decode the JWT.
                .setSigningKey(SECRET_KEY).build()// Uses the secret key to verify the token’s signature (ensures it’s not tampered with).
                .parseClaimsJws(token)// Parses the JWT token and extracts the claims (data inside the token).
                .getBody();//Retrieves the body (payload) of the token where claims (username, role, expiration) are stored.
        return claimsResolver.apply(claims);
    }
}
