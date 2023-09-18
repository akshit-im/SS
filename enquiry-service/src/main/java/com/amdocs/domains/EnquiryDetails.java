package com.amdocs.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "ENQ_DETAIL")
public class EnquiryDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@Column(name = "ENQ_TYPE", nullable = false)
	private String enqType;

	// private List<EnquiryTags> enqTags;

	@Column(name = "ENQ_USER_ID", nullable = false)
	private Long userId;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "DATE_ENTRY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEntry;

	@Column(name = "DATE_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdate;

	@Column(name = "DATE_FOLLOWUP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFollowup;

	@Column(name = "ASSIGNED_TO")
	private String assignedTo;

	@Column(name = "STATUS")
	private String status;
}
