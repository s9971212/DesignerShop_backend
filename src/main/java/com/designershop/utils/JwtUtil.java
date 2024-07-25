package com.designershop.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.designershop.security.MyUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String ISSUER = "DesignerShop";
	private static final String SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).toString();
	private static final long EXPIRATION_TIME = 864_000_000; // 10 days

	// 生成 JWT
	public static String generateToken(Authentication authentication) {
		MyUser myUser = (MyUser) authentication.getPrincipal();

		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(myUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).setIssuer(ISSUER)
				.setIssuedAt(new Date()).signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}

	// 解析 JWT
	public static Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseSignedClaims(token)
				.getPayload();
	}

	// 驗證 JWT 是否有效
	public static boolean validateToken(String token, MyUser myUser) {
		String tokenUsername = parseToken(token).getSubject();
		return StringUtils.equals(tokenUsername, myUser.getUsername()) && !isTokenExpired(token);
	}

	// 檢查 JWT 是否過期
	private static boolean isTokenExpired(String token) {
		Date expiration = parseToken(token).getExpiration();
		return expiration.before(new Date());
	}
}