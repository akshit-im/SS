package com.amdocs.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class BrandDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotBlank
	@NotEmpty
	private String name;

	@NotBlank
	@NotEmpty
	private String description;

	@NotBlank
	@NotEmpty
	private Integer status;

	@NotBlank
	@NotEmpty
	private Integer displayType;

	private Date doe;

	private Date dom;

	public Brand brand() {
		Brand obj = new Brand();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	public Brand brand(Brand obj) {
		this.id = obj.getId();
		BeanUtils.copyProperties(this, obj);
		return obj;
	}
}