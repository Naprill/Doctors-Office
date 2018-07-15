package com.chernivtsi.doctorsoffice.config;

import com.chernivtsi.doctorsoffice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationEntryPoint for User {@link User} to display specific Authentication exception
 */
@Slf4j
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
		    throws IOException {
       if (authenticationException instanceof DisabledException) {
           log.trace("User: {} blocked by admin" , request.getParameter("email"));
           response.sendRedirect("/login?disabled");
       }
       else {
           log.trace("User: {} input invalid credentials" , request.getParameter("email"));
           response.sendRedirect("/login?badCredentials");
       }
    }
}