package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Feedback;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.FeedbackDTO;
import com.chernivtsi.doctorsoffice.repository.FeedbackRepository;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class FeedbackService extends DefaultCrudSupport<Feedback> {
	private FeedbackRepository repository;
	private UserService userService;

	public FeedbackService(FeedbackRepository repository, UserService userService) {
		super(repository);
		this.repository = repository;
		this.userService = userService;
	}

	public void create(FeedbackDTO feedbackDTO, SecurityUser currentUser) {
		String author = "Анонім";
		if (!feedbackDTO.getAnonymous()) {
			User user = userService.findById(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("no such user in db"));
			author = user.getFirstName() + " " + user.getLastName();
		}
		Feedback feedback = new Feedback(author, feedbackDTO.getText(), LocalDate.now());
		create(feedback);
	}
}
