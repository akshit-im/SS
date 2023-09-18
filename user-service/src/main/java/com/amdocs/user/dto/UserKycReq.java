package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserKyc;

import lombok.Data;

@Data
public class UserKycReq implements Serializable {

	private static final long serialVersionUID = -5086748375392164548L;

	private UUID id;

	@NotBlank
	private User user;

	@NotBlank
	private String docName;

	@NotBlank
	private String docNumber;

	private Integer status;

	public UserKyc userKyc() {
		UserKyc obj = new UserKyc();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public UserKyc userKyc(UserKyc obj) {
		this.id = obj.getId();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

}
