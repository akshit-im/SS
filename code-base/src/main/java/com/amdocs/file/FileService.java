package com.amdocs.file;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Value("${upload.path}")
	private String uploadPath;

	public void save(MultipartFile file, String url, String name) throws FileUploadException {
		try {
			Path root = Paths.get(uploadPath + url);
			File dir = new File(uploadPath + url);
			if (!dir.exists())
				dir.mkdirs();
			Path resolve = root.resolve(name);
			CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
			Files.copy(file.getInputStream(), resolve, options);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public void remove(String url, String name) throws FileUploadException {
		try {
			Path root = Paths.get(uploadPath + url);
			File dir = new File(uploadPath + url);
			if (!dir.exists())
				dir.mkdirs();
			Path resolve = root.resolve(name);
			Files.delete(resolve);
		} catch (Exception e) {
			throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public String getUploadPath() {
		return uploadPath;
	}
}