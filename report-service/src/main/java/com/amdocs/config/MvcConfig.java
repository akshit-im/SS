package com.amdocs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amdocs.interceptor.AuthenticateInterceptor;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthenticateInterceptor authenticateInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticateInterceptor);
	}

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins(AppConstant.CORS_ORIGINS_ARY).allowedMethods(AppConstant.CORS_METHODS_ARY);
//		WebMvcConfigurer.super.addCorsMappings(registry);
//	}
}