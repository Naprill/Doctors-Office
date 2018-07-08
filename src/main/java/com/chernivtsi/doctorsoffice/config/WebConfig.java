package com.chernivtsi.doctorsoffice.config;

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
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/logout").setViewName("home");
        registry.addViewController("/register").setViewName("page-register");
        registry.addViewController("/forget").setViewName("pages-forget");
    }
}
