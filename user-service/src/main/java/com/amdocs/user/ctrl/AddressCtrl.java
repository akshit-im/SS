package com.amdocs.user.ctrl;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.user.dto.UserAddressReq;
import com.amdocs.user.entity.Address;
import com.amdocs.user.repo.UserService;

@RequestMapping("/api/user/addrs")
@RestController
class AddressCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = {"/list/{userId}"})
	public ResponseEntity<?> list(@PathVariable(value = "userId", required = true) UUID userId) throws Throwable {

		Address addrs = new Address(userId);
		return ResponseEntity.ok(userSvc.addrs(Example.of(addrs)));
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @RequestBody UserAddressReq addrs) throws Throwable {
		try {
			return ResponseEntity.ok(userSvc.addrs(userSvc.saveUpdate(addrs.addrs()).getId()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@Valid @RequestBody UserAddressReq addrs) throws Throwable {
		try {
			Address addrsObj = userSvc.addrs(addrs.getId());
			return ResponseEntity.ok(userSvc.addrs(userSvc.saveUpdate(addrs.addrs(addrsObj)).getId()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}
}