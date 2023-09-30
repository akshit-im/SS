package com.amdocs.user.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.user.entity.Status;
import com.amdocs.user.repo.UserService;

@RestController
@RequestMapping("/status")
class StatusCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = "/list")
	public ResponseEntity<?> typeList() throws Throwable {
		return ResponseEntity.ok(userSvc.status());
	}

	@GetMapping(value = "/list/{input}")
	public ResponseEntity<?> typeList(@PathVariable String input) throws Throwable {
		Status status = new Status();
		status.setRefTable(input.toUpperCase());
		return ResponseEntity.ok(userSvc.status(Example.of(status), Sort.by("name")));
	}

}