package com.amdocs.geo.dto;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "STATE_VIEW")
public class StateView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "NUM_CODE")
	private Integer numCode;

	@Column(name = "TXT_CODE")
	private String txtCode;

	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

}
