package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserProfession;
import com.amdocs.user.entity.UserWorkExpc;

import lombok.Data;

@Data
public class UserWorkExpcReq implements Serializable {

	private static final long serialVersionUID = 3296734510645075591L;

	private UUID id;

	@NotBlank
	private User user;

	@NotBlank
	private UserProfession profession;

	@NotBlank
	private String orgName;

	@NotBlank
	private String profile;

	@NotBlank
	private String roles;

	@NotBlank
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date frmDt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uptoDt;

	public UserWorkExpc userWorkExpc() {
		UserWorkExpc obj = new UserWorkExpc();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public UserWorkExpc userWorkExpc(UserWorkExpc obj) {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}
}
