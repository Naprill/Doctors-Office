package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserProfileDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Comparator;
import java.util.List;


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
		List<AnalysisDTO> analyses = userService.getUserFiles(userId);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		log.trace("Analyses: {}", analyses);
		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("user", user);
		modelAndView.addObject("analyses", analyses);
		log.trace("UserProfileDto: {}", user);
		return modelAndView;
	}

	@PostMapping
	public String updateProfile(@Valid @ModelAttribute("user") UserProfileDTO dto,
	                            BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			log.trace("Incorrect input in profile form ");
			redirectAttributes.addAttribute("status", "error");
		} else {
			log.trace("ProfileDto:{}", dto);
			userService.updateUserProfile(dto);
			log.trace("Successfully updated profile");
			redirectAttributes.addAttribute("status", "success");
		}
		return "redirect:/profile";
	}

}
