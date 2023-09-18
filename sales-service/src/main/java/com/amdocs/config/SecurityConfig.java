package com.amdocs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.amdocs.general.AppConstant;
import com.amdocs.jwt.util.AuthTokenFilter;

import jakarta.ws.rs.HttpMethod;

@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Autowired
	private AuthTokenFilter authTokenFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

		// @formatter:off
		httpSecurity.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource())) 
		.authorizeHttpRequests(httpRequest -> 
				httpRequest.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				           .requestMatchers("/api/auth/token", "/api/auth/validateToken", "/api/auth/loadUserByUsername/*", "/logout", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
				           .requestMatchers("/api/user/*").hasAnyAuthority("ADMINISTRATOR")
				           .anyRequest().fullyAuthenticated()) 		
 		.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(jwtAuthEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
 
		// @formatter:on
		httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(AppConstant.CORS_ORIGINS);
		corsConfig.setAllowedMethods(AppConstant.CORS_METHODS);
		corsConfig.setAllowedHeaders(AppConstant.CORS_HEADERS);
 
		corsConfig.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new com.amdocs.exception.AccessDeniedHandler();
	}
}