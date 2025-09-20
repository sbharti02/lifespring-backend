package com.lifespring.security;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    public long EXPIRATION_TIME =   60 * 60 * 1000; // 1 hour
//    // Implement JWT generation and validation logic here
//    public String generateToken(String username){
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS256,secretKey)
//                .compact();
//    }
//
//    public String extractUsername(String token){
//       return Jwts.parserBuilder()
//               .setSigningKey(secretKey)
//               .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//
//
//
//    }
//
//    public boolean validateToken(String token,String username){
//        String tokenUsername = extractUsername(token);
//        return (tokenUsername.equals(username) && !isTokenExpired(token));
//    }
//
//    public boolean isTokenExpired(String token){
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//
//        return expiration.before(new Date());
//    }
//

    @Value("${jwt.secret}")
    private String secretKey;

    // 1 hour validity
    private final long EXPIRATION_TIME = 5 * 60 * 1000;

    //  Convert secretKey to SecretKey (same for sign & validate)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //  Generate JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 👉 Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    //  Check if token expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}

