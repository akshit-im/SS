package com.amdocs.user.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.user.entity.Address;
import com.amdocs.user.entity.Address.AddressType;
import com.amdocs.user.entity.User;

import lombok.Data;

@Data
public class UserAddressReq {

	private UUID id;

	@NotBlank
	private State state;

	@NotBlank
	private City city;

	@NotBlank
	private Country country;

	private String address;

	@NotBlank
	private Integer pinCode;

	@NotBlank
	private User user;

	@NotBlank
	private AddressType addrsType;

	public Address addrs() {
		Address addrs = new Address();
		this.id = null;
		BeanUtils.copyProperties(this, addrs);
		return addrs;
	}

	public Address addrs(Address addrs) {
		BeanUtils.copyProperties(this, addrs);
		return addrs;
	}
}
