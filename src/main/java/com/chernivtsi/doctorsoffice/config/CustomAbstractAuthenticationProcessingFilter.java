package com.chernivtsi.doctorsoffice.config;

import com.chernivtsi.doctorsoffice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for authentication of User {@link User} to redirect
 * to {@link AuthenticationEntryPoint} with specific Authentication exception
 */
@Slf4j
public class CustomAbstractAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private UsernamePasswordAuthenticationFilter delegate;
	private AuthenticationEntryPoint authenticationEntryPoint;

	public CustomAbstractAuthenticationProcessingFilter(final UsernamePasswordAuthenticationFilter delegate,
	                                                    final AuthenticationEntryPoint authenticationEntryPoint) {
		this.delegate = delegate;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			return delegate.attemptAuthentication(request, response);
		} catch (AuthenticationException e) {
			try {
				authenticationEntryPoint.commence(request, response, e);
			} catch (Exception e1) {
				log.trace("Failed to redirect user on login with specific error, exception: {}", e1);
			}
		}
		return null;
	}

}