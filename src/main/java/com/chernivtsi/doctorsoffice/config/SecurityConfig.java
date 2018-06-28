package com.chernivtsi.doctorsoffice.config;

import com.chernivtsi.doctorsoffice.service.AdminService;
import com.chernivtsi.doctorsoffice.service.UserService;
import com.chernivtsi.doctorsoffice.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configures application's security
 */
@Configuration
@EnableAsync
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private AdminService adminService;

    @Override
    protected org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userService, adminService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FormLoginConfigurer<HttpSecurity> loginConfigurer = http.formLogin();
        loginConfigurer
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler()) //TODO check
                .and()
                .logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers("/public").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/").authenticated()
                .and()
                .csrf();

    }
}