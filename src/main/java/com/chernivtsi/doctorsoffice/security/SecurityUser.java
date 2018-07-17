package com.chernivtsi.doctorsoffice.security;

import com.chernivtsi.doctorsoffice.model.Admin;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Simple adapter between application's user and spring security's
 * authentication principal. Instance of this class will be returned from
 * {@link Authentication#getPrincipal()}
 */
@Getter
@Setter
public class SecurityUser extends org.springframework.security.core.userdetails.User {

	private Long id;

	public SecurityUser(final User user) {
		super(user.getEmail(),
				user.getHashedPassword(),
				user.isEnabled(), true, true, true,
				AuthorityUtils.createAuthorityList("USER"));
		this.id = user.getId();
	}

	public SecurityUser(final Admin admin) {
		super(admin.getEmail(),
				admin.getHashedPassword(),
				AuthorityUtils.createAuthorityList("ADMIN"));
	}

}
