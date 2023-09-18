package com.amdocs.user.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "APP_ROLES")
public class Role implements Serializable {

	public Role() {
	}

	public Role(String id) {
		super();
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.UUID, generator = "increment")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "NAME", unique = true)
	private String name;

	@Column(name = "STATUS")
	private String status;

}