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
@Table(name = "ENQ_ACTION")
public class EnquiryActions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private UUID id;

	@Column(name = "COMMENT", nullable = false)
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

	@Column(name = "ASSIGNED_BY")
	private String assignedBy;

	@Column(name = "STATUS")
	private String status;

}
