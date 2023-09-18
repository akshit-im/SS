package com.amdocs.domains;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ENQ_USER")
public class EnquiryUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@Column(name = "NAME", nullable = false, updatable = true)
	private String name;

	@Size(min = 10, max = 10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	@Column(name = "MOBILE", unique = true, nullable = false)
	private Long mobile;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "STATE")
	private String state;

	@Column(name = "CITY")
	private String city;

	@Size(min = 6, max = 6)
	@Pattern(regexp = "(^$|[0-9]{6})")
	@Column(name = "PINCODE")
	private Long pincode;

}
