package com.amdocs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"${spring.application.base-package}"})
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public GlobalFilter globalFilter() {
		return (exchange, chain) -> {
			System.out.println("Global filter " + System.currentTimeMillis());
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				// ServerHttpRequest request = exchange.getRequest();
				// ServerHttpResponse response = exchange.getResponse();
				// HttpHeaders headers = response.getHeaders();
				//
				// // Remove duplicate headers
				// headers.entrySet().removeIf(entry -> entry.getValue().size() > 1);

			}));
		};
	}

}
