package com.amdocs.jwt.dto;

import com.amdocs.general.AppConstant;

import lombok.Data;

@Data
public class TokenRefreshRes {

	private final String token;

	private final String refreshToken;

	private final String prefix = AppConstant.AUTH_TYPE_BEARER;

	public TokenRefreshRes(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}

}
