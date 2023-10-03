package com.amdocs.user.ctrl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.ErrorResponse;
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

	@GetMapping(value = "/list/{input}")
	public ResponseEntity<?> typeList(@PathVariable String input, @RequestHeader(value = "filterBy") String filterBy) throws Throwable {
		String[] inputArr = null;
		if (input.contains("_")) {
			inputArr = input.split("_");
		} else {
			inputArr = new String[]{input};
		}

		switch (filterBy.toUpperCase()) {
			case "ID" : {
				return ResponseEntity.ok(userSvc.typesByRef(inputArr));
			}
			case "NAME" : {
				return ResponseEntity.ok(userSvc.typesByRef(inputArr));
			}
			default : {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
			}
		}
	}

	@GetMapping(value = "/{input}")
	public ResponseEntity<?> getById(@PathVariable String input, @RequestHeader(value = "filterBy") String filterBy) throws Throwable {
		switch (filterBy.toUpperCase()) {
			case "ID" : {
				return ResponseEntity.ok(userSvc.type(UUID.fromString(input)));
			}
			case "NAME" : {
				return ResponseEntity.ok(userSvc.type(input));
			}
			default : {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
			}
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> addType(@RequestBody Type type) throws Throwable {
		return ResponseEntity.ok(userSvc.saveUpdate(type));
	}

}