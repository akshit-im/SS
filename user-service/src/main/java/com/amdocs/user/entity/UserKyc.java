package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_KYC", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "DOC_NAME"})})
public class UserKyc implements Serializable, AppEntity {

	public UserKyc() {
		super();
	}

	public UserKyc(UUID id) {
		super();
		this.id = id;
	}

	public UserKyc(User user) {
		super();
		this.user = user;
	}

	private static final long serialVersionUID = -5086748375392164548L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@CreationTimestamp
	@Column(name = "ENTRY_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@Column(name = "DOC_NAME", nullable = false)
	private String docName;

	@Column(name = "DOC_NUMBER", nullable = false)
	private String docNumber;

	@Column(name = "IFSC")
	private String ifsc;

	@Column(name = "STATUS")
	private Integer status;

	@UpdateTimestamp
	@Column(name = "DOM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dom;

}
