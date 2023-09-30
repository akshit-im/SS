package com.amdocs.filter;

import java.util.Date;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthGatewayFilter extends AbstractGatewayFilterFactory<AuthGatewayFilter.Config> {

	private final WebClient.Builder webClientBuilder;

	public AuthGatewayFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			System.out.println("AuthGatewayFilter " + new Date());
			if (!exchange.getRequest().getURI().toString().contains("/images/")) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					throw new RuntimeException("Missing Authorization information");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				String[] parts = authHeader.split(" ");
				if (parts.length != 2 || !"Bearer".equals(parts[0])) {
					throw new RuntimeException("Incorrect authorization structure");
				}
			}
			return chain.filter(exchange);
		};
	}

	public static class Config {
		// empty class as I don't need any particular configuration
	}
}