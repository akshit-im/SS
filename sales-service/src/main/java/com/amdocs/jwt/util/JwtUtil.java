package com.amdocs.jwt.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	private static final long serialVersionUID = 1L;

	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.refreshExpiration}")
	private Long refreshExpiration;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.authority}")
	private String authority;

	@Bean
	public JwtParser jwtParser() {
		return Jwts.parser().setSigningKey(secret);
	}

	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("unchecked")
	public List<String> getClaim(String token, String claimName) {
		Jws<Claims> claimsJws = jwtParser().parseClaimsJws(token);
		Claims claims = claimsJws.getBody();
		return (List<String>) claims.get(claimName);
	}

	private Claims getAllClaims(String token) {
		return jwtParser().parseClaimsJws(token).getBody();
	}

	// @formatter:off
	public String generateToken(String subject, String audience, Map<String, Object> claims) {
 		return Jwts.builder()
				.setIssuer(authority)
				.setSubject(subject)
				.setAudience(audience)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.addClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
	}
	// @formatter:on

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}

	public boolean validateToken(String authToken) {
		try {
			jwtParser().parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
			throw new SignatureException("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
			throw new MalformedJwtException("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
			throw new UnsupportedJwtException("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
			throw new IllegalArgumentException("JWT claims string is empty: " + e.getMessage());
		}
		return false;
	}
}