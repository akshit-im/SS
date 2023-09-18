package com.amdocs.jwt.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amdocs.general.AppConstant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		if (request.getMethod().equals(HttpMethod.OPTIONS)) {

		} else if (request.getHeader(HttpHeaders.AUTHORIZATION).startsWith(AppConstant.AUTH_TYPE_BEARER) && !request.getServletPath().contains("loadUserByUsername")) {
			String jwt = parseJwt(request);
			if (jwt == null && request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
					if (cookie.getName().equals("Token")) {
						jwt = cookie.getValue();
						break;
					}
				}
			} else if (jwt != null && jwtUtil.validateToken(jwt)) {
				HttpHeaders headers = new HttpHeaders();
				headers.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
				headers.set(AppConstant.AUDIENCE, request.getHeader(AppConstant.AUDIENCE));
				headers.set("Content-Type", "application/json");
				HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
				ResponseEntity<Object> userDetails = restTemplate.exchange("http://AUTH-SERVICE/api/auth/loadUserByUsername/" + jwtUtil.getUsername(jwt), HttpMethod.GET, httpEntity, Object.class);
				Map<String, Object> user = (Map<String, Object>) userDetails.getBody();
				List<String> claims = jwtUtil.getClaim(jwt, "authorities");
				List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
				claims.stream().forEach(System.out::print);
				for (String claim : claims) {
					list.add(new SimpleGrantedAuthority(claim.toUpperCase()));
				}
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get("username"), user.get("password"), list);
				// authentication.getAuthorities().stream().forEach(System.out::println);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else
				throw new AuthenticationServiceException("Cannot set user authentication");
		}
		chain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AppConstant.AUTH_TYPE_BEARER)) {
			return headerAuth.substring(7, headerAuth.length());
		} else
			return null;

	}

}