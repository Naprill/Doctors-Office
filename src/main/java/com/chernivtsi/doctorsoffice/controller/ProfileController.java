package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.model.dto.TherapyDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserProfileDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.TherapyService;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private TherapyService therapyService;

	public ProfileController(UserService userService,
	                         TherapyService therapyService) {
		this.userService = userService;
		this.therapyService = therapyService;
	}


	/**
	 * GET request for profile page of currently logged in patient
	 *
	 * @param currentUser - patient
	 * @return model and view
	 */
	@GetMapping()
	public ModelAndView getProfile(@AuthenticationPrincipal SecurityUser currentUser) {

		Long userId = currentUser.getId();
		UserProfileDTO user = userService.getUserDTOById(userId);
		List<AnalysisDTO> analyses = userService.getUserFiles(userId);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		log.trace("Analyses: {}", analyses);
		List<TherapyDTO> therapies = therapyService.getTherapiesByPatient(userId);
		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("user", user);
		modelAndView.addObject("analyses", analyses);
		modelAndView.addObject("therapies", therapies);
		log.trace("UserProfileDto: {}", user);
		return modelAndView;
	}

	/**
	 * GET request for patient profile page for Admin
	 *
	 * @param id - id of patient
	 * @return - model and view
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ModelAndView getProfileForAdmin(@PathVariable Long id) {
		UserProfileDTO user = userService.getUserDTOById(id);
		List<AnalysisDTO> analyses = userService.getUserFiles(id);
		analyses.sort(Comparator.comparing(AnalysisDTO::getDate).reversed());
		log.trace("Analyses: {}", analyses);
		List<TherapyDTO> therapies = therapyService.getTherapiesByPatient(id);
		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("user", user);
		modelAndView.addObject("analyses", analyses);
		modelAndView.addObject("therapies", therapies);
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

	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseBody
	@PostMapping("/therapy")
	public ResponseEntity createTherapy(@RequestBody TherapyDTO therapy) {
		therapyService.saveTherapy(therapy);
		log.trace("Therapy: {}", therapy.toString());
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/therapy/{id}")
	public ResponseEntity<TherapyDTO> getTherapy(@PathVariable Long id) {
		TherapyDTO therapyDTO = therapyService.getTherapyById(id);
		return new ResponseEntity<>(therapyDTO, HttpStatus.OK);
	}

	@PutMapping("/therapy")
	public ResponseEntity updateTherapy(@RequestBody TherapyDTO therapy) {
		log.info("Controller updateTherapy(): {}", therapy.toString());
		therapyService.updateTherapy(therapy);
		return new ResponseEntity(HttpStatus.OK);
	}
}
