package com.amdocs.user.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValidationResponse {

	private String status;
	private boolean isAuthenticated;
	private String methodType;
	private String username;
	private String token;
	private List<GrantedAuthority> authorities;

}
