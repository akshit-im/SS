package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.amdocs.entity.AppEntity;
import com.amdocs.geo.entity.City;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USER")
public class User implements Serializable, AppEntity {

	public User() {
		super();
	}

	public User(String id) {
		super();
		this.id = UUID.fromString(id);
	}

	public User(UUID id) {
		super();
		this.id = id;
	}

	private static final long serialVersionUID = 3296734510645075591L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private List<Role> roles;

	@Column(name = "CODE", unique = true, nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
	private String code;

	@Column(name = "NAME", nullable = false, columnDefinition = "VARCHAR(100)")
	private String name;

	@Column(name = "MOBILE", unique = true, nullable = false)
	private Long mobile;

	@Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)")
	private String email;

	@Column(name = "LOGIN_ID", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
	private String loginId;

	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false, columnDefinition = "VARCHAR(50)")
	private String password;

	@Column(name = "WEBSITE")
	private String website;

	@CreationTimestamp
	@Column(name = "ENTRY_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	// @Enumerated(EnumType.STRING)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Status.class)
	@JoinColumn(name = "STATUS", nullable = false)
	private Status status;

	@Column(name = "ACCOUNT_LOCKED", nullable = false, columnDefinition = "tinyint(1) default 1")
	private Boolean accountLocked;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "REF_ID")
	private User refId;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Type.class)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	private Type type;

	@Column(name = "ORG_TYPE", columnDefinition = "VARCHAR(50)")
	private String orgType;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = City.class)
	@JoinColumn(name = "CITY_ID")
	private City city;
}