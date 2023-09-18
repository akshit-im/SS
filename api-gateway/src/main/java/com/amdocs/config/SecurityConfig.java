//package com.amdocs.config;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
//import org.springframework.util.StringUtils;
//
//import com.amdocs.jwt.util.AuthTokenFilter;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import reactor.core.publisher.Mono;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private JwtAuthEntryPoint jwtAuthEntryPoint;
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
//	}
//
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//		return http.csrf().disable().authorizeExchange()
//				.pathMatchers("/api/public/**").permitAll()
//				.pathMatchers("/api/admin/**")
//				.hasRole("ADMIN").anyExchange().authenticated().and()
//				.exceptionHandling().authenticationEntryPoint(null).addFilterAt(authenticationWebFilter(), SecurityWebFilterChain.Order.AUTHENTICATION).securityContextRepository(securityContextRepository()).build();
//	}
//
//	private ServerAuthenticationConverter serverAuthenticationConverter() {
//		return exchange -> {
//			String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//			if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
//				String authToken = authHeader.substring(7);
//				return Mono.just(new UsernamePasswordAuthenticationToken(authToken, authToken));
//			} else {
//				return Mono.empty();
//			}
//		};
//	}
//
//	@Bean
//	public ReactiveAuthenticationManager authenticationManager() {
//		return authentication -> {
//			String token = authentication.getCredentials().toString();
//			if (StringUtils.hasLength(token)) {
//				return Mono.empty();
//			}
//			try {
//				Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//				String username = claims.getSubject();
//				List<String> roles = (List) claims.get("roles");
//				UserDetails userDetails = User.builder().username(username).password("").roles(roles.toArray(new String[0])).build();
//				return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities()));
//			} catch (Exception e) {
//				return Mono.empty();
//			}
//		};
//	}
//}
