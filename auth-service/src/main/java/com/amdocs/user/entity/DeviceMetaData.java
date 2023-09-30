package com.amdocs.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DEVICE_METADATA")
public class DeviceMetaData implements Serializable {

	public DeviceMetaData() {
		super();
	}

	public DeviceMetaData(UUID id) {
		this.id = id;
	}

	public DeviceMetaData(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	@GeneratedValue
	@Column(name = "ID", nullable = false, updatable = false)
	private UUID id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "AUSER_ID", nullable = false)
	private User user;

	@Column(name = "DEVICE_DETAIL", nullable = false)
	private String deviceDetail;

	@Column(name = "LOCATION", nullable = false)
	private String location;

	@Column(name = "LAST_LOGIN", nullable = false)
	private Date lastLogin;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DeviceMetaData that = (DeviceMetaData) o;
		return Objects.equals(id, that.id) && Objects.equals(getUser().getId(), that.getUser().getId()) && Objects.equals(deviceDetail, that.deviceDetail) && Objects.equals(location, that.location) && Objects.equals(lastLogin, that.lastLogin);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, getUser().getId(), deviceDetail, location, lastLogin);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DeviceMetadata{");
		sb.append("id=").append(id);
		sb.append(", userId=").append(getUser().getId());
		sb.append(", deviceDetails='").append(deviceDetail).append('\'');
		sb.append(", location='").append(location).append('\'');
		sb.append(", lastLoggedIn=").append(lastLogin);
		sb.append('}');
		return sb.toString();
	}
}