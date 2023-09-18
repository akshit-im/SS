package com.amdocs.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amdocs.entity.Brand;
import com.amdocs.entity.BrandDto;
import com.amdocs.exception.ErrorResponse;
import com.amdocs.file.FileService;
import com.amdocs.general.AppConstant;
import com.amdocs.repo.ProductService;
import com.google.common.io.Files;

@RequestMapping("/api/product/brand")
@RestController
class BrandCtrl {

	@Autowired
	private FileService fileSvc;

	@Autowired
	private ProductService prdSvc;

	@PostMapping(value = "/add")
	public ResponseEntity<?> addBrand(@Validated @ModelAttribute BrandDto brandDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Throwable {
		try {
			System.out.println(brandDto);
			Brand brand = prdSvc.saveUpdate(brandDto.brand());
			if (file != null)
				fileSvc.save(file, "/product/brand/" + brand.getId(), "logo." + Files.getFileExtension(file.getOriginalFilename()));
			return ResponseEntity.ok(brand);
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error" + e.getMessage()));
		}
	}

	@GetMapping(value = "/list")
	public ResponseEntity<?> listBrand(@RequestHeader(value = "offset", required = false) Integer offset, @RequestHeader(value = "limit", required = false) Integer limit) throws Throwable {
		try {
			if (offset == null) {
				offset = 0;
			} else {
				offset = offset - 1;
			}
			if (limit == null) {
				limit = AppConstant.DEFAULT_RECORD_LIMIT;
			}
			return ResponseEntity.ok(prdSvc.brand(PageRequest.of(offset, limit, Sort.by("name").descending())));
		} catch (Exception e) {
			return ResponseEntity.ok(new ErrorResponse(500, "Internal Server Error" + e.getMessage()));
		}
	}
}
