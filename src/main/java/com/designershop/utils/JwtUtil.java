package com.designershop.utils;

import com.designershop.security.MyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Component
public class JwtUtil {

    private final Key secretKey;
    private final long expirationTime;
    private static final String ISSUER = "DesignerShop";

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTime = expirationTime;
    }

    // 生成 JWT
    public String generateToken(Authentication authentication) {
        MyUser myUser = (MyUser) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(myUser.getUsername()).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuer(ISSUER).setIssuedAt(new Date()).signWith(secretKey, SignatureAlgorithm.HS512).compact();
    }

    // 解析 JWT
    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(token).getPayload();
    }

    // 驗證 JWT 是否有效
    public boolean validateToken(String token, MyUser myUser) {
        String tokenUsername = parseToken(token).getSubject();
        return StringUtils.equals(tokenUsername, myUser.getUsername()) && !isTokenExpired(token);
    }

    // 檢查 JWT 是否過期
    private boolean isTokenExpired(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.before(new Date());
    }
}