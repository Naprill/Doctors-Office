package com.chernivtsi.doctorsoffice.config;

import com.chernivtsi.doctorsoffice.model.token.ExpirationTokenProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for web mvc
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("authentication/page-login");
		registry.addViewController("/").setViewName("layouts/layoutDashboard");
		registry.addViewController("/logout").setViewName("home");
		registry.addViewController("/forget").setViewName("authentication/pages-forget");
	}

	@Bean
	public ExpirationTokenProperties getExpirationTokenProperties() {
		return new ExpirationTokenProperties();
	}

	@Bean
	public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
		defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
		return defaultMethodSecurityExpressionHandler;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setDefaultRolePrefix("");
		return defaultWebSecurityExpressionHandler;
	}
}
