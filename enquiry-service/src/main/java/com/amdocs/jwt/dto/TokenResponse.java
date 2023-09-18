package com.amdocs.jwt.dto;

import java.util.Date;

import com.amdocs.general.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class TokenResponse {

	public TokenResponse(String token, String refreshToken, String clientId) {
		this.access_token = token;
		this.refresh_token = refreshToken;
		this.clientId = clientId;
	}

	@Setter(value = AccessLevel.NONE)
	private final String access_token;

	@Setter(value = AccessLevel.NONE)
	private final String refresh_token;

	@Setter(value = AccessLevel.NONE)
	private final String clientId;

	@Setter(value = AccessLevel.NONE)
	private final String token_type = AppConstant.AUTH_TYPE_BEARER;

	@JsonFormat(pattern = AppConstant.DATE_PATTERN)
	private Date timestamp = new Date();

}