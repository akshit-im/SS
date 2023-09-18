package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.geo.entity.Country;
import com.amdocs.user.entity.Role;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.User.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqisterReq extends TokenReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private List<Role> roles;

	private Type type = Type.Individual;

	@NotBlank
	private Country country;

	@NotBlank
	private String name;

	private String code;

	@NotBlank
	private Long mobile;

	private Boolean accountLocked = true;

	@Email
	@NotBlank
	private String email;

	public User entity() {
		User obj = new User();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public User entity(User obj) {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}
}
