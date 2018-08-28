package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.UserProfileDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/profile")
public class ProfileController {

	private UserService userService;

	public ProfileController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public ModelAndView getProfile(@AuthenticationPrincipal SecurityUser currentUser) {

		Long userId = currentUser.getId();
		UserProfileDTO user = userService.getUserDTOById(userId);
		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("user", user);
		log.trace("UserProfileDto: {}", user);
		return modelAndView;
	}
}
