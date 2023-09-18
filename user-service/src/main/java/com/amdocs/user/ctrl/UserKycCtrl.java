package com.amdocs.user.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.file.FileService;
import com.amdocs.user.dto.UserKycReq;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserKyc;
import com.amdocs.user.repo.UserService;
import com.google.common.io.Files;

@RequestMapping("/user/kyc")
@RestController
class UserKycCtrl {

	@Autowired
	private FileService fileSvc;

	@Autowired
	private UserService userSvc;

	@Value("${upload.path}")
	private String uploadPath;

	@PostMapping(value = "/add")
	public ResponseEntity<?> addKyc(@Validated @ModelAttribute UserKycReq kycDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Throwable {
		try {
			UserKyc userKyc = userSvc.kycDesc(userSvc.saveUpdate(kycDto.userKyc()).getId());
			if (file != null)
				fileSvc.save(file, "/user/kyc/" + userKyc.getUser().getCode(), kycDto.getDocName().toLowerCase().replace(" ", "-") + "." + Files.getFileExtension(file.getOriginalFilename()));
			return ResponseEntity.ok(userKyc);
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateKyc(@Validated @ModelAttribute UserKycReq kycDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Throwable {
		try {
			UserKyc userKyc = userSvc.kycDesc(kycDto.getId());
			UserKyc userViewKyc = userSvc.kycDesc(userSvc.saveUpdate(kycDto.userKyc(userKyc)).getId());
			if (file != null)
				fileSvc.save(file, "/user/kyc/" + userViewKyc.getUser().getCode(), kycDto.getDocName().toLowerCase().replace(" ", "-") + "." + Files.getFileExtension(file.getOriginalFilename()));
			return ResponseEntity.ok(userViewKyc);
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@PutMapping(value = "/update-pic")
	public ResponseEntity<?> updatePic(@Validated @ModelAttribute UserKycReq kycDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Throwable {
		try {
			UserKyc userViewKyc = null;
			UserKyc userKyc = userSvc.kycDesc(kycDto.getUser().getId());
			if (userKyc != null) {
				userViewKyc = userSvc.kycDesc(userSvc.saveUpdate(kycDto.userKyc(userKyc)).getId());
			} else {
				userViewKyc = userSvc.kycDesc(userSvc.saveUpdate(kycDto.userKyc()).getId());
			}
			if (file != null)
				fileSvc.save(file, "/user/kyc/" + userViewKyc.getUser().getCode(), kycDto.getDocName().toLowerCase().replace(" ", "-") + "." + Files.getFileExtension(file.getOriginalFilename()));
			return ResponseEntity.ok(userViewKyc);
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error"));
		}
	}

	@GetMapping(value = "/list/{userId}")
	public ResponseEntity<?> userKycList(@PathVariable UUID userId) throws Throwable {
		return ResponseEntity.ok(userSvc.kycDesc(new User(userId)));
	}

	@GetMapping(value = "/desc/{userId}/{docNumber}")
	public ResponseEntity<?> userKycDesc(@PathVariable UUID userId, @PathVariable String docNumber) throws Throwable {
		return ResponseEntity.ok(userSvc.kycDesc(new User(userId), docNumber));
	}

	@RequestMapping(value = "/view/{kycId}/{docNumber}.pdf", method = RequestMethod.GET)
	public void showKycPdf(@PathVariable UUID kycId, @PathVariable String docNumber, HttpServletResponse res) throws ServletException {
		res.setContentType("application/pdf");
		res.setHeader("Content-disposition", "inline; filename=" + kycId + "-" + docNumber + ".pdf");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		res.addDateHeader("Expires", cal.getTimeInMillis());
		try {
			UserKyc obj = userSvc.kycDesc(kycId);
			// Blob img = obj.getSupDoc();
			// byte[] bytes = img.getBytes(1, (int) img.length());
			// InputStream inputStream = new ByteArrayInputStream(bytes);
			String path = uploadPath + "/user/kyc/" + obj.getUser().getCode() + "/" + (obj.getDocName().toLowerCase().replace(" ", "-")) + ".pdf";
			System.out.println(path);
			InputStream in = new FileInputStream(path);
			IOUtils.copy(in, res.getOutputStream());
		} catch (Exception e) {
			InputStream in = getClass().getResourceAsStream("/pdf-default.pdf");
			try {
				IOUtils.copy(in, res.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/view/{userId}/{docNumber}.jpg", method = RequestMethod.GET)
	public void showKycJpg(@PathVariable UUID userId, @PathVariable String docNumber, HttpServletResponse res) throws ServletException {
		res.setContentType("image/jpg");
		res.addHeader("Cache-Control", "max-age=31536000, must-revalidate, private");
		try {
			UserKyc obj = userSvc.kycDesc(new User(userId), docNumber);
			res.addDateHeader("Last-Modified", obj.getDom().getTime());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			res.addDateHeader("Expires", cal.getTimeInMillis());
			// Blob img = obj.getSupDoc();
			// byte[] bytes = img.getBytes(1, (int) img.length());
			// InputStream inputStream = new ByteArrayInputStream(bytes);
			String path = uploadPath + "/user/kyc/" + obj.getUser().getCode() + "/" + (obj.getDocName().toLowerCase().replace(" ", "-")) + ".jpg";
			System.out.println(path);
			InputStream in = new FileInputStream(path);
			IOUtils.copy(in, res.getOutputStream());
		} catch (Exception e) {
			InputStream in = getClass().getResourceAsStream("/user-profile.jpg");
			try {
				IOUtils.copy(in, res.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}