package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "APP_STATUS", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "REF_TABLE"})})
public class Status extends AppEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public Status() {
		super();
	}

	public Status(String name) {
		super();
		this.name = name;
	}

	public Status(String name, String refTable) {
		super();
		this.name = name;
		this.refTable = refTable;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", parameters = {@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private UUID id;

	@Column(name = "NAME", unique = true, nullable = false, columnDefinition = "VARCHAR(20)")
	private String name;

	@Column(name = "REF_TABLE", nullable = false, columnDefinition = "VARCHAR(20)")
	private String refTable;

}