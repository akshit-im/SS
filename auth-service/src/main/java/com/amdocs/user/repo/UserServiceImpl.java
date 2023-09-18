package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserPrincipal;

import jakarta.transaction.Transactional;

@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User userByCode(String userCode) throws Throwable {
		return userRepo.findByCode(userCode);
	}

	@Override
	public User userById(UUID userId) throws Throwable {
		return userRepo.findById(userId).get();
	}

	@Override
	public User userByMobile(Long mobile) throws Throwable {
		return userRepo.findByMobile(mobile);
	}

	@Override
	public User userByEmail(String email) throws Throwable {
		return userRepo.findByEmail(email);
	}

	@Override
	public Page<User> user(Pageable pageable) throws Throwable {
		return userRepo.findAll(pageable);
	}

	@Override
	public Page<User> user(Example<User> example, Pageable pageable) throws Throwable {
		return userRepo.findAll(example, pageable);
	}

	@Override
	public List<User> user(Example<User> user) throws Throwable {
		return userRepo.findAll(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userRepo.findByLoginId(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new UserPrincipal(user);
	}

}
