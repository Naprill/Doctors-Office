package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.repository.UserRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends DefaultCrudSupport<User>{

	private UserRepository userRepository;

	public UserService(UserRepository repository) {
		super(repository);
		this.userRepository = repository;
	}

	public Optional<User> findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
}
