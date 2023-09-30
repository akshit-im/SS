package com.amdocs.geo.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "GEO_CITY", uniqueConstraints = {@UniqueConstraint(columnNames = {"STATE_ID", "NAME"})})
public class City extends AppEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public City() {
		super();
	}

	public City(UUID id) {
		super();
		this.id = id;
	}

	public City(String name) {
		super();
		this.name = name;
	}

	public City(State state) {
		super();
		this.state = state;
	}

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = State.class)
	@JoinColumn(name = "STATE_ID", nullable = false)
	private State state;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "CODE")
	private String code;

	@Column(name = "STD")
	private String STD;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "STATUS", nullable = false)
	private Integer status;
}