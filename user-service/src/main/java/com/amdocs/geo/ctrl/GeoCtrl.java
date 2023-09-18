package com.amdocs.geo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.geo.dto.CityView;
import com.amdocs.geo.dto.StateView;
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

	@PostMapping(value = "/state")
	public ResponseEntity<?> state(@RequestBody StateView stateView) throws Throwable {
		return ResponseEntity.ok(geoSvc.state(stateView));
	}

	@PostMapping(value = "/city")
	public ResponseEntity<?> city(@RequestBody CityView cityView) throws Throwable {
		return ResponseEntity.ok(geoSvc.city(cityView));
	}
}
