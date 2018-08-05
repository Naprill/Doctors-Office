package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Role;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.UserRegistrationDTO;
import com.chernivtsi.doctorsoffice.model.token.ConfirmAccountToken;
import com.chernivtsi.doctorsoffice.repository.AccountTokenRepository;
import com.chernivtsi.doctorsoffice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

	private UserRepository userRepository;

	private EmailService emailService;

	private PasswordEncoder passwordEncoder;

	private AccountTokenRepository accountTokenRepository;

	public AccountService(UserRepository userRepository,
	                      EmailService emailService,
	                      PasswordEncoder passwordEncoder,
	                      AccountTokenRepository accountTokenRepository) {
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.accountTokenRepository = accountTokenRepository;
	}

	/**
	 * Method which transmits information to Repository layer to save User in DB
	 */
	@Transactional
	public void register(UserRegistrationDTO registration, HttpServletRequest request) {

		User user = new User();

        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setPatronymic(registration.getPatronymic());
        user.setBirthDate(registration.getBirthDate());
	    user.setTelephone(registration.getTelephone());
	    user.setAddress(registration.getAddress());

		user.setEmail(registration.getEmail());
		user.setHashedPassword(passwordEncoder.encode(registration.getPassword().getUniquePassword()));
		user.setRegistrationDate(LocalDate.now());
		user.setRoles(Collections.singletonList(Role.USER));

		log.trace("Saving user with data: {}", user);
		saveTokenAndSendEmail(
				userRepository.save(user),
				request
		);
	}

	/**
	 * Method saves confirmation token
	 * and sends to user email for confirmation of their account
	 * @param user - receiver of email
	 * @param request
	 * @throws EntityNotFoundException
	 */
	public void saveTokenAndSendEmail(User user, HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		ConfirmAccountToken token = new ConfirmAccountToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUser(user);
		accountTokenRepository.save(token);

		emailService.createAndSendEmail(token, user, url);

	}
}
