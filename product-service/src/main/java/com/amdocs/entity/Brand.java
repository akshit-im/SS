package com.amdocs.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "PRD_BRANDS")
public class Brand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "DISPLAY_TYPE")
	private Integer displayType;

	@CreationTimestamp
	@Column(name = "DOE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doe;

	@UpdateTimestamp
	@Column(name = "DOM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dom;
}