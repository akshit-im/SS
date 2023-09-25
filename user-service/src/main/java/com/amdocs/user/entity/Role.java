package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.UUID;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "APP_ROLES")
public class Role implements Serializable, AppEntity {

	public Role() {
	}

	public Role(UUID id) {
		super();
		this.id = id;
	}

	public Role(String name, String status) {
		super();
		this.name = name;
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", nullable = false, unique = true)
	private UUID id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "STATUS")
	private String status;

}