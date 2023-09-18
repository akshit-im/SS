package com.amdocs.user.ctrl;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.user.dto.ProfessionReq;
import com.amdocs.user.dto.UserWorkExpcReq;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserProfession;
import com.amdocs.user.entity.UserWorkExpc;
import com.amdocs.user.repo.UserService;

@RequestMapping("/user/work-expc")
@RestController
class WorkExpcCtrl {

	@Autowired
	private UserService userSvc;

	@GetMapping(value = {"/list/{userId}/{userCode}"})
	public ResponseEntity<?> list(@PathVariable(value = "userId", required = true) UUID userId, @PathVariable(value = "userCode", required = true) String userCode) throws Throwable {
		UserWorkExpc workExpcView = new UserWorkExpc(new User(userId));
		return ResponseEntity.ok(userSvc.workExpcList(Example.of(workExpcView)));
	}

	@GetMapping(value = {"/desc/{userId}/{id}"})
	public ResponseEntity<?> desc(@PathVariable(value = "userId", required = true) UUID userId, @PathVariable(value = "id", required = true) UUID id) throws Throwable {
		return ResponseEntity.ok(userSvc.workExpc(id));
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@Valid @ModelAttribute UserWorkExpcReq workExpcDto) throws Throwable {
		try {
			return ResponseEntity.ok(userSvc.workExpc(userSvc.saveUpdate(workExpcDto.userWorkExpc()).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@Valid @ModelAttribute UserWorkExpcReq workExpcDto) throws Throwable {
		try {
			UserWorkExpc workExpcObj = userSvc.workExpc(workExpcDto.getId());
			return ResponseEntity.ok(userSvc.workExpc(userSvc.saveUpdate(workExpcDto.userWorkExpc(workExpcObj)).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = {"/profession"})
	public ResponseEntity<?> listProfession() throws Throwable {
		return ResponseEntity.ok(userSvc.profession());
	}

	@GetMapping(value = {"/profession/{id}"})
	public ResponseEntity<?> listProfession(@PathVariable UUID id) throws Throwable {
		return ResponseEntity.ok(userSvc.profession(id));
	}

	@PostMapping(value = "/profession-add")
	public ResponseEntity<?> addProfession(@Valid @ModelAttribute ProfessionReq professionDto) throws Throwable {
		try {
			return ResponseEntity.ok(userSvc.profession(userSvc.saveUpdate(professionDto.profession()).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@PutMapping(value = "/profession-update")
	public ResponseEntity<?> updateProfession(@Valid @ModelAttribute ProfessionReq professionDto) throws Throwable {
		try {
			UserProfession professionObj = userSvc.profession(professionDto.getId());
			return ResponseEntity.ok(userSvc.profession(userSvc.saveUpdate(professionDto.profession(professionObj)).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

}
