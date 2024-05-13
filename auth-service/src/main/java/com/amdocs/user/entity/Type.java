package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "APP_TYPE", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "REFERENCE"})})
public class Type extends AppEntity implements Serializable {

	public Type() {
		super();
	}

	public Type(String name) {
		super();
		this.name = name;
	}

	public Type(UUID id) {
		super();
		this.id = id;
	}

	public Type(Type reference) {
		super();
		this.reference = reference;
	}

	public Type(String name, String status) {
		super();
		this.name = name;
		this.status = status;
	}

	public Type(String name, Type reference) {
		super();
		this.name = name;
		this.status = "Active";
		this.reference = reference;
	}

	public Type(String name, String status, Type reference) {
		super();
		this.name = name;
		this.status = status;
		this.reference = reference;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Type.class)
	@JoinColumn(name = "REFERENCE")
	private Type reference;

	@Column(name = "STATUS", nullable = false)
	private String status;

}