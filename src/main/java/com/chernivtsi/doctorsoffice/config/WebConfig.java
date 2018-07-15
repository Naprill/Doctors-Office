package com.chernivtsi.doctorsoffice.config;

import com.chernivtsi.doctorsoffice.model.token.ExpirationTokenProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for web mvc
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("page-login");
		registry.addViewController("/").setViewName("layouts/layoutDashboard");
		registry.addViewController("/logout").setViewName("home");
		registry.addViewController("/forget").setViewName("pages-forget");
	}

	@Bean
	public ExpirationTokenProperties getExpirationTokenProperties() {
		return new ExpirationTokenProperties();
	}
}
