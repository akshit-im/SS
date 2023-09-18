package com.amdocs.geo.dto;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@Embeddable
public class CountryEmbd implements Serializable {

	public CountryEmbd() {
		super();
	}

	public CountryEmbd(Long countryId) {
		super();
		this.countryId = countryId;
	}

	public CountryEmbd(String countryName) {
		super();
		this.countryName = countryName;
	}

	private static final long serialVersionUID = 1L;

	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

}
