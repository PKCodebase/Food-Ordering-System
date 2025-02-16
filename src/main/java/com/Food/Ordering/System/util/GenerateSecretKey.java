package com.Food.Ordering.System.util;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;

public class GenerateSecretKey {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded()); // Proper Base64 encoding
        System.out.println("Generated Key: " + base64Key);
    }
}
