package com.amdocs.geo.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

	public Country(String id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "CODE")
	private String code;

	@Column(name = "CODE_2")
	private String code2;

	@Column(name = "ISD")
	private String isd;

	@Column(name = "PH_CODE")
	private String phCode;

	@Column(name = "CAPITAL")
	private String capital;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@JsonIgnore
	@Exclude
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<State> states;

	@Column(name = "REGION")
	private String region;

	@Column(name = "SUB_REGION")
	private String subRegion;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "LATITUDE")
	private String latitude;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, targetEntity = TimeZone.class)
	@JoinTable(name = "GEO_COUNTRY_ZONE", joinColumns = @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "TIME_ZONE_ID", referencedColumnName = "ID"))
	private List<TimeZone> timeZone = new LinkedList<TimeZone>();

	public String getIsdName() {
		return isd + " (" + name + ")";
	}

}