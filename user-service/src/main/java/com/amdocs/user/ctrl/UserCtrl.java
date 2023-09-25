package com.amdocs.user.ctrl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.user.dto.ReqisterReq;
import com.amdocs.user.dto.UserFilter;
import com.amdocs.user.dto.UserSearchReq;
import com.amdocs.user.entity.Role;
import com.amdocs.user.entity.Type;
import com.amdocs.user.entity.User;
import com.amdocs.user.repo.UserService;

@RequestMapping("/api/user")
@RestController
class UserCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = {"/docs"})
	public ResponseEntity<?> docs() throws Throwable {
		return ResponseEntity.ok(UserFilter.getInstance());
	}

	@GetMapping(value = {"/list"})
	public ResponseEntity<?> list(@RequestHeader(value = "offset", required = false) Integer offset, @RequestHeader(value = "limit", required = false) Integer limit) throws Throwable {
		try {
			if (offset == null) {
				offset = 0;
			}
			if (limit == null) {
				limit = 100;
			}
			return ResponseEntity.ok(userSvc.user(PageRequest.of(offset, limit, Sort.by("entryDate").descending())));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = {"/list/{roleId}"})
	public ResponseEntity<?> list(@PathVariable(value = "roleId", required = true) String roleId, @RequestHeader(value = "offset", required = false) Integer offset, @RequestHeader(value = "limit", required = false) Integer limit) throws Throwable {
		try {
			if (offset == null) {
				offset = 0;
			}
			if (limit == null) {
				limit = 100;
			}
			User user = new User();
			Role role = new Role();
			role.setId(UUID.fromString(roleId));
			List<Role> roles = new ArrayList<>();
			roles.add(role);
			user.setRoles(roles);
			return ResponseEntity.ok(userSvc.user(Example.of(user), PageRequest.of(offset, limit, Sort.by("entryDate").descending())));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = "/code/{userCode}")
	public ResponseEntity<?> code(@PathVariable String userCode) throws Throwable {
		User user = userSvc.byCode(userCode);
		if (user == null) {
			return ResponseEntity.ok(new ErrorResponse(404, "User Not Found"));
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/user-id/{userId}")
	public ResponseEntity<?> userId(@PathVariable String userId) throws Throwable {
		Optional<User> user = userSvc.userById(UUID.fromString(userId));
		if (!user.isPresent()) {
			return ResponseEntity.ok(new ErrorResponse(404, "User Not Found"));
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/search-user")
	public ResponseEntity<?> searchUser(@Valid @RequestBody UserSearchReq user) throws Throwable {
		try {
			return ResponseEntity.ok(userSvc.search(user));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error " + e.getMessage()));
		}
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> registerUser(@RequestBody ReqisterReq reqisterReq) throws Throwable {

		User user = reqisterReq.user();
		MessageDigest digest = null;
		Optional<Type> type = userSvc.typeById(reqisterReq.getType().getId());
		if (type.isEmpty()) {
			throw new RuntimeException("Incorrect Account Type Provided");
		}
		user.setType(type.get());
		switch (type.get().getName()) {
			case "Assets" :
				digest = MessageDigest.getInstance("MD5");
				break;
			default :
				digest = MessageDigest.getInstance("SHA-256");
				break;

		}
		UUID userId = UUID.nameUUIDFromBytes(digest.digest(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
		user.setId(userId);
		User registeredUser = userSvc.saveUpdate(user, true);
		return ResponseEntity.ok(userSvc.userById(registeredUser.getId()));
	}

	@PutMapping(value = "/update-profile")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody ReqisterReq user) throws Throwable {
		try {
			Optional<User> userObj = userSvc.userById(user.getId());
			if (userObj.isPresent())
				return ResponseEntity.ok(userSvc.userById(userSvc.saveUpdate(user.entity(userObj.get()), false).getId()));
			else
				return ResponseEntity.ok(userSvc.userById(userSvc.saveUpdate(user.entity(userObj.get()), false).getId()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

}