package com.amdocs.geo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		return ResponseEntity.ok(geoSvc.country());
	}

	@GetMapping(value = "/state")
	public ResponseEntity<?> state(@RequestBody Country country) throws Throwable {
		return ResponseEntity.ok(geoSvc.state(country));
	}

	@GetMapping(value = "/city")
	public ResponseEntity<?> city(@RequestBody State state) throws Throwable {
		return ResponseEntity.ok(geoSvc.city(state));
	}
}
