package com.chernivtsi.doctorsoffice.controller;


import com.chernivtsi.doctorsoffice.model.Feedback;
import com.chernivtsi.doctorsoffice.model.dto.FeedbackDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	private static final String FEEDBACK = "feedback";
	private static final String REDIRECT_FEEDBACK = "redirect:/feedback";
	private static final String STATUS = "status";

	private FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@GetMapping
	public ModelAndView getFeedback() {
		List<Feedback> list = feedbackService.findAll();
		log.trace("getFeedback() {}", list);
		ModelAndView modelAndView = new ModelAndView(FEEDBACK);
		modelAndView.addObject(FEEDBACK, list);
		return modelAndView;
	}

	@ModelAttribute
	public FeedbackDTO newFeedbackDTO() {
		return new FeedbackDTO();
	}

	@PostMapping
	public String createFeedback(@ModelAttribute @Valid FeedbackDTO feedback,
	                             BindingResult result,
	                             RedirectAttributes redirectAttributes,
	                             @AuthenticationPrincipal SecurityUser currentUser) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(STATUS, "failure");
			return REDIRECT_FEEDBACK;
		} else {
			try {
				log.trace("create feedback: {}", feedback);
				feedbackService.create(feedback, currentUser);
				redirectAttributes.addFlashAttribute(STATUS, "success");
			} catch (Exception e) {
				log.error(e.toString());
				redirectAttributes.addFlashAttribute(STATUS, "failure");
			}
			return REDIRECT_FEEDBACK;
		}
	}

}
