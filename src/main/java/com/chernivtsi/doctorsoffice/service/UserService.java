package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.UserProfileDTO;
import com.chernivtsi.doctorsoffice.repository.UserRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class UserService extends DefaultCrudSupport<User> {

	private UserRepository userRepository;

	public UserService(UserRepository repository) {
		super(repository);
		this.userRepository = repository;
	}

	public Optional<User> findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUserEnabled(final boolean enabled, final Long userId) {
		userRepository.updateUserEnabled(enabled, userId);
	}

	public UserProfileDTO getUserDTOById(Long userId) {
		User user = this.findById(userId).orElseThrow(EntityNotFoundException::new);
		log.trace("USER: {}", user);
		return new UserProfileDTO(user);
	}
}
