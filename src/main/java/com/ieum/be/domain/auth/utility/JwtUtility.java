package com.ieum.be.domain.auth.utility;

import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtility {
    private final SecretKey key;
    private final Long jwtAccessTokenExpiration;

    public JwtUtility(@Value("${jwt.secret}") String secret, @Value("${jwt.access_token_expiration}") long jwtAccessTokenExpiration) {
        byte[] keyBytes = Encoders.BASE64.encode(secret.getBytes()).getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.jwtAccessTokenExpiration = jwtAccessTokenExpiration;
    }

    public ResponseEntity<?> generateJwtResponse(Map<String, String> claims) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtAccessTokenExpiration)).signWith(key)
                .compact();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION, accessToken);

        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException |
                 io.jsonwebtoken.security.SignatureException e) {
            throw new GlobalException(GeneralResponse.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new GlobalException(GeneralResponse.EXPIRED_JWT_TOKEN);
        }
    }
}