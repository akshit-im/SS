package com.amdocs.jwt.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LogOutRequest {

	@NotBlank
	private Long userId;

	@NotBlank
	private String token;

}
