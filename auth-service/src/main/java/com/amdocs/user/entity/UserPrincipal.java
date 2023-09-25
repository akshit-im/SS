package com.amdocs.user.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	public UserPrincipal() {
		super();
	}

	private static final long serialVersionUID = 1L;

	private User user;

	public UserPrincipal(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		for (Role role : user.getRoles()) {
			list.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
		}
		return list;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.getAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		boolean rtn = false;
		try {
			switch (user.getStatus().getName()) {
				case "Active" :
					rtn = true;
					break;
				case "Deactive" :
					break;
				default :
					break;
			}
		} catch (Exception e) {
		}
		return rtn;
	}

	public User getUser() {
		return user;
	}
}
