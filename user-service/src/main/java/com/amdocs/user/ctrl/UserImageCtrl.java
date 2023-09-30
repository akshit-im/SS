package com.amdocs.user.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.file.FileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/user/images")
@RestController
class UserImageCtrl {

	@Autowired
	private FileService fileSvc;

	@GetMapping(value = "/{userId}/{name}.jpg")
	public void showKycJpg(@PathVariable String userId, @PathVariable String name, HttpServletResponse res) throws ServletException {
		res.setContentType("image/jpg");
		res.addHeader("Cache-Control", "max-age=31536000, must-revalidate, private");
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			res.addDateHeader("Expires", cal.getTimeInMillis());
			String path = fileSvc.getUploadPath() + "/user/images/" + userId + "/" + (name.toLowerCase().replace(" ", "-")) + ".jpg";
			FileTime lastModifiedTime = java.nio.file.Files.getLastModifiedTime(Paths.get(path));
			res.addDateHeader("Last-Modified", lastModifiedTime.toMillis());
			InputStream in = new FileInputStream(path);
			IOUtils.copy(in, res.getOutputStream());
		} catch (Exception e) {
			InputStream in = getClass().getResourceAsStream("/images/user-profile.jpg");
			try {
				IOUtils.copy(in, res.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
@RequestMapping("/user/img")
@RestController
class UserImgCtrl {

	@Autowired
	private FileService fileSvc;

	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadImage(@RequestParam String userId, @RequestParam String fileType, @RequestParam(value = "file") MultipartFile file) throws Throwable {
		try {
			if (file != null)
				switch (fileType) {
					case "PROFILE" : {
						fileSvc.save(file, "/user/images/" + userId, "profile.jpg");
					}
						break;
					default : {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param fileType is Wrong"));
					}
				}
			return ResponseEntity.ok("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Internal Server Error \n" + e.getMessage()));
		}
	}
}
