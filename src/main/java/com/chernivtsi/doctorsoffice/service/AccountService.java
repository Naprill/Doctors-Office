package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Role;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.UserRegistrationDto;
import com.chernivtsi.doctorsoffice.repository.UserRepository;
import com.chernivtsi.doctorsoffice.model.token.ExpirationTokenProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collections;

@Service
@Slf4j
public class AccountService {

    private ExpirationTokenProperties expirationTokenProperties;

    private UserRepository userRepository;

    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    public AccountService(UserRepository userRepository,
                          EmailService emailService,
                          ExpirationTokenProperties expirationTokenProperties,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.expirationTokenProperties = expirationTokenProperties;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method which transmits information to Repository layer to save User in DB
     */
    @Transactional
    public void register(UserRegistrationDto registration , HttpServletRequest request) {

    	User user = new User();

//        user.setFirstName(registration.getFirstName());
//        user.setLastName(registration.getLastName());
//        user.setPatronymic(registration.getPatronymic());
//        user.setBirthDate(registration.getBirthDate());
//	    user.setTelephone(registration.getTelephone());
//	    user.setAddress(registration.getAddress());!!!!!

	    user.setFirstName("Name");
	    user.setLastName("Surname");
	    user.setPatronymic("Patr");
	    user.setBirthDate(LocalDate.now());
	    user.setTelephone("0000000000");
	    user.setAddress("Chernivtsy");

	    user.setEmail(registration.getEmail());
	    user.setHashedPassword(passwordEncoder.encode(registration.getPassword().getUniquePassword()));
	    user.setRegistrationDate(LocalDate.now());
	    user.setRoles(Collections.singletonList(Role.USER));

	    log.info("Saving user with data: {}", user);
        sendEmailToUser(
        		userRepository.save(user),
		        expirationTokenProperties.getVerificationTime(),
		        request
        );
    }

    /**
     * Method which creates email, search specific User in DB and sends him email
     * @param user - receiver of email
     * @param time - expiry time period of password reset token
     * @param request
     * @throws EntityNotFoundException
     */
    public void sendEmailToUser(User user, Integer time, HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	    emailService.createAndSendEmail(user, time, url);
    }
}
