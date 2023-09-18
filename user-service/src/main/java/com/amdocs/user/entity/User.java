package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class User implements Serializable {

	public User() {
		super();
	}

	public User(UUID id) {
		super();
		this.id = id;
	}

	public enum Status {
		NewReg, Active, Deactive, Locked
	}

	public enum Type {
		Individual, Team, Organization, Application
	}

	private static final long serialVersionUID = 3296734510645075591L;

	@Id
	// @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private List<Role> roles;

	@Column(name = "CODE", unique = true, nullable = false, updatable = false)
	private String code;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "MOBILE", unique = true, nullable = false)
	private Long mobile;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "LOGIN_ID", unique = true, nullable = false)
	private String loginId;

	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@CreationTimestamp
	@Column(name = "ENTRY_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, columnDefinition = "varchar(10)")
	private Status status;

	@Column(name = "ACCOUNT_LOCKED", nullable = false, columnDefinition = "tinyint(1) default 1")
	private Boolean accountLocked;

	@Exclude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "MANAGER_ID")
	private User managerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, columnDefinition = "varchar(20) default 'Individual'")
	private Type type;

}