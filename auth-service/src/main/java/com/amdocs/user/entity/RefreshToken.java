package com.amdocs.user.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity(name = "REFRESH_TOKEN")
public class RefreshToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "TOKEN", nullable = false, unique = true)
	private String token;

	@Column(name = "EXPIRY_DATE", nullable = false)
	private Instant expiryDate;

}
