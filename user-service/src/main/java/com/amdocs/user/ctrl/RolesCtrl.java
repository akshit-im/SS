package com.amdocs.user.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.user.repo.UserService;

@RestController
@RequestMapping("/roles")
class RolesCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = "/list")
	public ResponseEntity<?> roleList() throws Throwable {
		return ResponseEntity.ok(userSvc.role());
	}

	@GetMapping(value = "/{roleId}")
	public ResponseEntity<?> getRole(@PathVariable String roleId) throws Throwable {
		return ResponseEntity.ok(userSvc.role(roleId));
	}

}