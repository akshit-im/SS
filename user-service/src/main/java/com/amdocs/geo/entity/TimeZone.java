package com.amdocs.geo.entity;

import java.io.Serializable;

import org.hibernate.annotations.UuidGenerator;

import com.amdocs.entity.AppEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "GEO_TIME_ZONE")
public class TimeZone extends AppEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false, unique = true)
	private String id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "GMT_OFFSET", nullable = false)
	private String gmtOffset;

	@Column(name = "GMT_NAME", nullable = false)
	private String gmtName;

	@Column(name = "ABBREVIATION", nullable = false)
	private String abbreviation;

	@Column(name = "TZ_NAME", nullable = false)
	private String tzName;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeZone other = (TimeZone) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		if (gmtName == null) {
			if (other.gmtName != null)
				return false;
		} else if (!gmtName.equals(other.gmtName))
			return false;
		if (gmtOffset == null) {
			if (other.gmtOffset != null)
				return false;
		} else if (!gmtOffset.equals(other.gmtOffset))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tzName == null) {
			if (other.tzName != null)
				return false;
		} else if (!tzName.equals(other.tzName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((gmtName == null) ? 0 : gmtName.hashCode());
		result = prime * result + ((gmtOffset == null) ? 0 : gmtOffset.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tzName == null) ? 0 : tzName.hashCode());
		return result;
	}
}
