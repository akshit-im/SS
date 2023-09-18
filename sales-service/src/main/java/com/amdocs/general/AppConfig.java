package com.amdocs.general;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import brave.Span;
import brave.Tracer;
import brave.sampler.Sampler;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AppConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Sampler createSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

	@Bean
	public Filter traceIdInResponseFilter(Tracer tracer) {
		return (request, response, chain) -> {
			Span currentSpan = tracer.currentSpan();
			if (currentSpan != null) {
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.addHeader("X-B3-TraceId", currentSpan.context().traceIdString());
				resp.addHeader("X-B3-SpanId", currentSpan.context().spanIdString());
				resp.addHeader("X-B3-ParentSpanId", currentSpan.context().parentIdString());
			}
			chain.doFilter(request, response);
		};
	}

}
