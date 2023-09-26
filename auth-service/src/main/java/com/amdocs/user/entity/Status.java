package com.amdocs.user.entity;

import java.io.Serializable;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "APP_STATUS", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "REF_TABLE"})})
public class Status implements Serializable, AppEntity {

	private static final long serialVersionUID = 1L;

	public Status() {
		super();
	}

	public Status(String name) {
		super();
		this.name = name;
	}

	public Status(String name, String refTable) {
		super();
		this.name = name;
		this.refTable = refTable;
	}

	@Id
	@Column(name = "NAME", unique = true, nullable = false, columnDefinition = "VARCHAR(20)")
	private String name;

	@Column(name = "REF_TABLE", nullable = false, columnDefinition = "VARCHAR(20)")
	private String refTable;

}