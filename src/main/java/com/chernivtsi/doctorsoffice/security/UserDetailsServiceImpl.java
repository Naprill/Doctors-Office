package com.chernivtsi.doctorsoffice.security;

import com.chernivtsi.doctorsoffice.service.AdminService;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.lang.String.format;

/**
 * Implementation of {@link UserDetailsService} to load application user by email
 */
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String email){
        return userService.findUserByEmail(email).map(SecurityUser::new)
                .orElseGet(() -> adminService.findAdminByEmail(email).map(SecurityUser::new)
                        .orElseThrow(() -> new UsernameNotFoundException(format("Couldn't find user with email %s", email))));
    //TODO find and authenticate user by telephone number also
    }
}