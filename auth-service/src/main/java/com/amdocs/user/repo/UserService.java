package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.amdocs.user.entity.User;

public interface UserService extends UserDetailsService {

	public User userByCode(String userCode) throws Throwable;

	public User userById(UUID userId) throws Throwable;

	public User userByMobile(Long mobile) throws Throwable;

	public User userByEmail(String email) throws Throwable;

	public Page<User> user(Pageable pageable) throws Throwable;

	public Page<User> user(Example<User> example, Pageable pageable) throws Throwable;

	public List<User> user(Example<User> user) throws Throwable;

}
