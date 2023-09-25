package com.amdocs.user.ctrl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.user.entity.Type;
import com.amdocs.user.repo.UserService;

@RestController
@RequestMapping("/types")
class TypeCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = "/list")
	public ResponseEntity<?> typeList() throws Throwable {
		return ResponseEntity.ok(userSvc.types());
	}

	@GetMapping(value = "/list/{id}")
	public ResponseEntity<?> typeList(@PathVariable String id) throws Throwable {
		return ResponseEntity.ok(userSvc.types(UUID.fromString(id)));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getType(@PathVariable String id) throws Throwable {
		return ResponseEntity.ok(userSvc.typeById(UUID.fromString(id)));
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> addType(@RequestBody Type type) throws Throwable {
		return ResponseEntity.ok(userSvc.saveUpdate(type));
	}

}