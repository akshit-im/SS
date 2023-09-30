package com.amdocs.geo.ctrl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.exception.ErrorResponse;
import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.geo.repo.GeoService;

@RestController
@RequestMapping("/geo")
class GeoCtrl {

	@Autowired
	private GeoService geoSvc;

	@GetMapping(value = "/country")
	public ResponseEntity<?> country() throws Throwable {
		List<Country> list = geoSvc.country();
		Collections.sort(list);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/states/{input}")
	public ResponseEntity<?> state(@PathVariable String input, @RequestHeader(value = "filterBy") String filterBy) throws Throwable {
		switch (filterBy.toUpperCase()) {
			case "ID" : {
				return ResponseEntity.ok(geoSvc.state(Example.of(new State(new Country(UUID.fromString(input)))), Sort.by("name")));
			}
			case "NAME" : {
				return ResponseEntity.ok(geoSvc.state(Example.of(new State(new Country(input))), Sort.by("name")));
			}
			default : {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
			}
		}
	}

	@GetMapping(value = "/cities/{input}")
	public ResponseEntity<?> city(@PathVariable String input, @RequestHeader(value = "filterBy") String filterBy) throws Throwable {
		switch (filterBy.toUpperCase()) {
			case "ID" : {
				return ResponseEntity.ok(geoSvc.city(Example.of(new City(new State(UUID.fromString(input)))), Sort.by("name")));
			}
			case "NAME" : {
				return ResponseEntity.ok(geoSvc.city(Example.of(new City(new State(input))), Sort.by("name")));
			}
			default : {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Header Param filterBy is Wrong"));
			}
		}
	}
}
