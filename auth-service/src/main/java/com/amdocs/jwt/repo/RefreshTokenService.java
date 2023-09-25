package com.amdocs.jwt.repo;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amdocs.exception.TokenRefreshException;
import com.amdocs.user.entity.RefreshToken;
import com.amdocs.user.entity.User;
import com.amdocs.user.repo.UserService;

@Service

public class RefreshTokenService {

	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.refreshExpiration}")
	private Long refreshExpiration;

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	@Autowired
	private UserService userSvc;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepo.findByToken(token);
	}

	@Transactional
	public RefreshToken createRefreshToken(User user) throws Throwable {
		RefreshToken refreshToken = new RefreshToken();
		System.out.println(user);
		refreshToken.setUser(user);
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration * 1000));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken = refreshTokenRepo.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(token);
			throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	@Transactional
	public int deleteByUser(User user) throws Throwable {
		return refreshTokenRepo.deleteByUser(user);
	}

	@Transactional
	public int deleteByUserId(String userId) throws Throwable {
		return refreshTokenRepo.deleteByUser(userSvc.userById(UUID.fromString(userId)));
	}
}
