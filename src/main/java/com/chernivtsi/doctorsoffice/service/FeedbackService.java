package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Feedback;
import com.chernivtsi.doctorsoffice.repository.FeedbackRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService extends DefaultCrudSupport<Feedback> {
	private FeedbackRepository repository;

	public FeedbackService(FeedbackRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
