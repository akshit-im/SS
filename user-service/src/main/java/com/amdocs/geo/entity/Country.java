package com.amdocs.geo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable {

	public Country() {
		super();
	}

	public Country(UUID id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "CODE")
	private String code;

	@Column(name = "ISD")
	private String isd;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@JsonIgnore
	@Exclude
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<State> states;

	public String getIsdName() {
		return isd + " (" + name + ")";
	}
}