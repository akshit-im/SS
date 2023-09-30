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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@RequestMapping("/user")
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = {"/list/{input}"})
	public ResponseEntity<?> list(@PathVariable(value = "input", required = true) String input, @RequestHeader(value = "filterBy") String filterBy, @RequestHeader(value = "offset", required = false) Integer offset, @RequestHeader(value = "limit", required = false) Integer limit) throws Throwable {
		try {
			if (offset == null) {
				offset = 0;
			}
			if (limit == null) {
				limit = 100;
			}
			User user = new User();
			switch (filterBy.toUpperCase()) {
				case "ROLE_ID" : {
					Role role = new Role();
					role.setId(UUID.fromString(input));
					List<Role> roles = new ArrayList<>();
					roles.add(role);
					user.setRoles(roles);
				}
					break;
				case "TYPE_ID" : {
					Type type = new Type(UUID.fromString(input));
					user.setType(type);
				}
					break;
				case "TYPE_NAME" : {
					Type type = new Type(input);
					user.setType(type);
				}
					break;
				case "REF_TYPE_ID" : {
					Type type = new Type(new Type(UUID.fromString(input)));
					user.setType(type);
				}
					break;
				case "REF_TYPE_NAME" : {
					Type type = new Type(new Type(input));
					user.setType(type);
				}
					break;
				default : {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
				}
			}
			return ResponseEntity.ok(userSvc.user(Example.of(user), PageRequest.of(offset, limit, Sort.by("entryDate").descending())));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = "/{input}")
	public ResponseEntity<?> code(@PathVariable String input, @RequestHeader(value = "filterBy") String filterBy) throws Throwable {
		User user = null;
		Optional<User> optUsr = null;
		switch (filterBy.toUpperCase()) {
			case "CODE" : {
				user = userSvc.byCode(input);
			}
				break;
			case "ID" : {
				optUsr = userSvc.userById(UUID.fromString(input));
				if (!optUsr.isPresent()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "User Not Found"));
				}
				user = optUsr.get();
			}
				break;
			default : {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
			}
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/search-user")
	public ResponseEntity<?> searchUser(@Valid @RequestBody UserSearchReq user) throws Throwable {
		try {
			return ResponseEntity.ok(userSvc.search(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Internal Server Error " + e.getMessage()));
		}
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> registerUser(@RequestBody ReqisterReq reqisterReq) throws Throwable {

		User user = reqisterReq.user();
		MessageDigest digest = null;
		Optional<Type> type = userSvc.type(reqisterReq.getType().getId());
		if (type.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Incorrect Account Type Provided"));
		}
		user.setType(type.get());
		switch (type.get().getReference().getName()) {
			case "Business" :
				digest = MessageDigest.getInstance("MD5");
				break;
			default :
				digest = MessageDigest.getInstance("SHA-256");
				break;
		}
		userSvc.butifyReg(user, type);
		UUID userId = UUID.nameUUIDFromBytes(digest.digest(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
		user.setId(userId);
		User registeredUser = userSvc.saveUpdate(user, true);
		return ResponseEntity.ok(userSvc.userById(registeredUser.getId()));
	}

	@PatchMapping(value = "/update")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody ReqisterReq user) throws Throwable {
		try {
			Optional<User> userObj = userSvc.userById(user.getId());
			if (userObj.isPresent())
				return ResponseEntity.ok(userSvc.userById(userSvc.saveUpdate(user.entity(userObj.get(), "loginId", "password", "entryDate"), false).getId()));
			else {
				userSvc.saveUpdate(user.entity(userObj.get()), false);
				return ResponseEntity.ok(userSvc.userById(user.getId()));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Internal Server Error", e.toString()));
		}
	}

}