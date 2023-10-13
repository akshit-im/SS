package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.geo.entity.City;
import com.amdocs.user.entity.Role;
import com.amdocs.user.entity.Status;
import com.amdocs.user.entity.Type;
import com.amdocs.user.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqisterReq extends TokenReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private List<Role> roles;

	@NotBlank
	private Type type;

	private String mcaType;

	private User reference;
	
	@NotBlank
	private Status status;

	@NotBlank
	private String name;

	private String code;

	@NotBlank
	private Long mobile;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private City city;

	private String website;

	private Boolean accountLocked = false;

	public User entity() {
		User obj = new User();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public User entity(User obj) {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public User entity(User obj, String... ignoreProperties) {
		BeanUtils.copyProperties(this, obj, ignoreProperties);
		return obj;
	}
}
