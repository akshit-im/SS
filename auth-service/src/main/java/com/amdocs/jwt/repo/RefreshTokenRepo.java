package com.amdocs.jwt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.RefreshToken;
import com.amdocs.user.entity.User;

@Repository
interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

	public Optional<RefreshToken> findByToken(String token);

	@Modifying
	public Integer deleteByUser(User user);
}
