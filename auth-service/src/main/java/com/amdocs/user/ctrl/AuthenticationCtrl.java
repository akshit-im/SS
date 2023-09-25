package com.amdocs.user.ctrl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.TokenRefreshException;
import com.amdocs.general.AppConstant;
import com.amdocs.jwt.dto.TokenRefreshReq;
import com.amdocs.jwt.dto.TokenRefreshRes;
import com.amdocs.jwt.dto.TokenResponse;
import com.amdocs.jwt.repo.RefreshTokenService;
import com.amdocs.jwt.util.JwtUtil;
import com.amdocs.user.entity.RefreshToken;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserPrincipal;
import com.amdocs.user.repo.UserService;
@RequestMapping("/auth")
@RestController
@CrossOrigin
class AuthenticationCtrl {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userSvc;

	@Autowired
	private RefreshTokenService rfshTknSvc;

	@GetMapping(value = "/isMobileExist")
	public ResponseEntity<?> isMobExist(@RequestParam Long mobile) throws Throwable {
		User user = userSvc.userByMobile(mobile);
		if (user == null) {
			return ResponseEntity.ok("Not Registered : " + mobile);
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/loadUserByUsername/{username}")
	public ResponseEntity<UserDetails> loadUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
		UserDetails user = userSvc.loadUserByUsername(username);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/token")
	public ResponseEntity<?> createAuthToken(@RequestHeader(value = "Audience") String audience, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization) throws Throwable {

		String[] parts = authorization.split(" ");
		if (parts.length != 2 || !AppConstant.AUTH_TYPE_BASIC.equals(parts[0])) {
			throw new RuntimeException("Incorrect Authorization Structure");
		}
		String[] credentials = new String(Base64.getDecoder().decode(parts[1].getBytes())).split(":");
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			List<String> list = new ArrayList<>();
			for (GrantedAuthority authrority : userPrincipal.getAuthorities()) {
				list.add(authrority.getAuthority());
			}
			Map<String, Object> claims = new HashMap<>();
			claims.put(AppConstant.JWT_AUTHORITIES, list);
			claims.put("clientId", userPrincipal.getUser().getId());
			String token = jwtUtil.generateToken(userPrincipal.getUsername(), audience, claims);
			rfshTknSvc.deleteByUser(userPrincipal.getUser());
			RefreshToken refreshToken = rfshTknSvc.createRefreshToken(userPrincipal.getUser());
			return ResponseEntity.ok(new TokenResponse(token, refreshToken.getToken(), userPrincipal.getUser().getId().toString()));
		} catch (AuthenticationException authenticationException) {
			throw authenticationException;
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshReq request, String audience) {
		String reqRefreshToken = request.getRefreshToken();
		return rfshTknSvc.findByToken(reqRefreshToken).map(rfshTknSvc::verifyExpiration).map(RefreshToken::getUser).map(user -> {
			String token = null; // jwtUtil.generateTokenFromUsername(user.getLoginId(), audience);
			return ResponseEntity.ok(new TokenRefreshRes(token, reqRefreshToken));
		}).orElseThrow(() -> new TokenRefreshException(reqRefreshToken, "Refresh token is not in database!"));
	}

}