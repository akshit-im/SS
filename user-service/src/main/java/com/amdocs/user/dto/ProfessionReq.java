package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.user.entity.UserProfession;

import lombok.Data;

@Data
public class ProfessionReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String shortDesc;

	@NotBlank
	private Integer status;

	public UserProfession profession() {
		UserProfession obj = new UserProfession();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public UserProfession profession(UserProfession obj) {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

}
