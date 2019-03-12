package com.chernivtsi.doctorsoffice.controller;


import com.chernivtsi.doctorsoffice.model.Feedback;
import com.chernivtsi.doctorsoffice.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	private FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@GetMapping
	public ModelAndView getFeedback() {
		List<Feedback> list = feedbackService.findAll();
		log.trace("getFeedback() {}", list);
		ModelAndView modelAndView = new ModelAndView("feedback");
		modelAndView.addObject("feedback", list);
		return modelAndView;
	}
}
