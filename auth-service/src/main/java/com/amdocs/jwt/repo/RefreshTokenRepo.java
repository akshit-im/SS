package com.amdocs.jwt.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.RefreshToken;
import com.amdocs.user.entity.User;

@Repository
interface RefreshTokenRepo extends JpaRepository<RefreshToken, UUID> {

	public Optional<RefreshToken> findByToken(String token);

	@Modifying
	public Integer deleteByUser(User user);
}
