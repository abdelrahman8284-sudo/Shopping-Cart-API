package com.abdelrahman.shoppingcart.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

	@Value("${security.jwt.secret-key}")
	private  String securityKey;
	
	public String generateToken(String email,String role,Long userId) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("role", role.toUpperCase());
		claims.put("userId",userId);
		return Jwts.builder()
				.claims()
				.add(claims)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*30))
				.subject(email)
				.and()
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		
		byte[] keyBytes = Decoders.BASE64.decode(securityKey);
		return Keys.hmacShaKeyFor(keyBytes );
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String email  = extractEmail(token);		
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		
		Claims claims = extractAllClaims(token);
		
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
		        .verifyWith(getKey())   // بيتأكد إن التوقيع صحيح بنفس الـsecret
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();

	}
	public String extractEmail(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		
		return extractClaim(token,Claims::getExpiration);
	}
	
}
