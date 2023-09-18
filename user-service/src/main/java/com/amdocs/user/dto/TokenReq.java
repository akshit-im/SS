package com.amdocs.user.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.user.entity.User;

import lombok.Data;

@Data
public class TokenReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;

	public User user() {
		User obj = new User();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

}