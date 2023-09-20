package com.amdocs.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class BrandDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private Integer status;

	@NotBlank
	private Integer displayType;

	public Brand get() {
		Brand obj = new Brand();
		this.id = null;
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public Brand get(Brand obj) {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}
}