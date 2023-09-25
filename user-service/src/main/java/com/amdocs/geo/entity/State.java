package com.amdocs.geo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Entity
@Table(name = "GEO_STATE", uniqueConstraints = {@UniqueConstraint(columnNames = {"COUNTRY_ID", "NAME"})})
public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	public State() {
		super();
	}

	public State(UUID id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = Country.class)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	private Country country;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "CODE")
	private String code;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@Column(name = "NUM_CODE")
	private Integer numCode;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "TXT_CODE")
	private String txtCode;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LATITUDE")
	private String latitude;

	@JsonIgnore
	@Exclude
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = City.class)
	private List<City> cities;

}