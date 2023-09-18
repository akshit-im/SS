package com.amdocs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.tracing.Tracer;

@RestController
@RequestMapping("/api/enquiry")
public class EnquiryController {

	@Autowired
	private Tracer tracer;

	@PostMapping("/add")
	public String enquiryAdd() {
		return "add ## " + tracer.currentSpan().context().traceId() + " ## " + tracer.currentSpan().context().spanId();
	}

	@PostMapping("/list")
	public String enquiryList() {
		return " list ## " + tracer.currentSpan().context().traceId() + " ## " + tracer.currentSpan().context().spanId();
	}

}
