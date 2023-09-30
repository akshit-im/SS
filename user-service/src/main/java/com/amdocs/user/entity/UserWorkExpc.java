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
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USER_WORK_EXPERIENCE", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "PROFESSION_ID", "ORG_NAME", "FRM_DT"})})
public class UserWorkExpc extends AppEntity implements Serializable {

	private static final long serialVersionUID = 3296734510645075591L;

	public UserWorkExpc() {
		super();
	}

	public UserWorkExpc(UUID id) {
		super();
		this.id = id;
	}

	public UserWorkExpc(User user) {
		super();
		this.user = user;
	}

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = UserProfession.class)
	@JoinColumn(name = "PROFESSION_ID", nullable = false)
	private UserProfession profession;

	@Column(name = "ORG_NAME", nullable = false)
	private String orgName;

	@Column(name = "PROFILE", nullable = false)
	private String profile;

	@Column(name = "ROLES")
	private String roles;

	@Column(name = "FRM_DT", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date frmDt;

	@Column(name = "UPTO_DT")
	@Temporal(TemporalType.DATE)
	private Date uptoDt;

	@CreationTimestamp
	@Column(name = "ENTRY_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@UpdateTimestamp
	@Column(name = "DOM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dom;

}
