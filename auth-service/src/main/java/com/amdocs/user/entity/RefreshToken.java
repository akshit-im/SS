package com.amdocs.user.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "REFRESH_TOKEN")
public class RefreshToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private Long id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name = "TOKEN", nullable = false, unique = true)
	private String token;

	@Column(name = "EXPIRY_DATE", nullable = false)
	private Instant expiryDate;

}
