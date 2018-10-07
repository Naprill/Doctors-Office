package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserImmutableProfileDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserUpdatableProfileDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/profile")
public class ProfileController {

	private static final String VIEW_NAME = "profile";
	private static final String USER_IMMUTABLE = "userImmutable";
	private static final String USER_UPDATABLE = "userUpdatable";
	private static final String ANALYSES = "analyses";
	private UserService userService;

	public ProfileController(UserService userService) {
		this.userService = userService;
	}


	/**
	 * GET request for profile page of currently logged in patient
	 * @param currentUser - patient
	 * @return model and view
	 */
	@GetMapping()
	public ModelAndView getProfile(@AuthenticationPrincipal SecurityUser currentUser) {

		Long userId = currentUser.getId();
		UserUpdatableProfileDTO userUpdatable = userService.getUserUpdatableProfileDTOById(userId);
		UserImmutableProfileDTO userImmutable = userService.getUserImmutableProfileDTOById(userId);
		List<AnalysisDTO> analyses = userService.getUserFiles(userId);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		log.trace("Analyses: {}", analyses);
		ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
		modelAndView.addObject(USER_UPDATABLE, userUpdatable);
		modelAndView.addObject(USER_IMMUTABLE, userImmutable);
		modelAndView.addObject(ANALYSES, analyses);
		log.trace("UserUpdatableProfileDTO: {}", userUpdatable);
		return modelAndView;
	}

	/**
	 * GET request for patient profile page for Admin
	 * @param id - id of patient
	 * @return - model and view
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ModelAndView getProfileForAdmin(@PathVariable Long id) {

		UserUpdatableProfileDTO userUpdatable = userService.getUserUpdatableProfileDTOById(id);
		UserImmutableProfileDTO userImmutable = userService.getUserImmutableProfileDTOById(id);
		List<AnalysisDTO> analyses = userService.getUserFiles(id);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		log.trace("Analyses: {}", analyses);
		ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
		modelAndView.addObject(USER_UPDATABLE, userUpdatable);
		modelAndView.addObject(USER_IMMUTABLE, userImmutable);
		modelAndView.addObject(ANALYSES, analyses);
		log.trace("UserUpdatableProfileDTO: {}", userUpdatable);
		return modelAndView;
	}

	@PostMapping
	public ModelAndView updateProfile(@Valid @ModelAttribute("userUpdatable") UserUpdatableProfileDTO dto,
	                            BindingResult result) {
		ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
		Long userId = dto.getId();
		UserImmutableProfileDTO userImmutable = userService.getUserImmutableProfileDTOById(userId);
		List<AnalysisDTO> analyses = userService.getUserFiles(userId);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		modelAndView.addObject(USER_IMMUTABLE, userImmutable);
		modelAndView.addObject(ANALYSES, analyses);

		if (result.hasErrors()) {
			log.trace("Incorrect input in profile form ");
			return modelAndView;
		} else {
			log.trace("ProfileDto:{}", dto);
			userService.updateUserProfile(dto);
			log.trace("Successfully updated profile");
			modelAndView.addObject("status", "success");
			return modelAndView;
		}
	}

}
