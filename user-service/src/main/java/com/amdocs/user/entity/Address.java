package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Entity
@Table(name = "USER_ADDRESS")
public class Address implements Serializable {

	public Address() {
		super();
	}

	public Address(UUID id) {
		super();
		this.id = id;
	}

	public Address(User user) {
		super();
		this.user = user;
	}

	public Address(UUID id, User user) {
		super();
		this.id = id;
		this.user = user;
	}

	public enum AddressType {
		Home, Business, Billing, Shiping, Contact
	}

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@CreationTimestamp
	@Column(name = "ENTRY_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = State.class)
	@JoinColumn(name = "STATE_ID")
	private State state;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = City.class)
	@JoinColumn(name = "CITY_ID")
	private City city;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = Country.class)
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PINCODE")
	private Integer pinCode;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "ADDRS_TYPE", nullable = false)
	private AddressType addrsType;

}
