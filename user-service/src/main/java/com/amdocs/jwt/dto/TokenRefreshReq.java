package com.amdocs.jwt.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TokenRefreshReq {

	@NotBlank
	private String refreshToken;
}
