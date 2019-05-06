package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.ReceptionDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.ScheduleService;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
public class UserReceptionController {

	private ScheduleService scheduleService;
	private UserService userService;

	public UserReceptionController(ScheduleService scheduleService,
	                               UserService userService) {
		this.scheduleService = scheduleService;
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/personal-receptions")
	public ModelAndView getUserReceptions(@AuthenticationPrincipal SecurityUser currentUser,
	                                      @PageableDefault Pageable pageRequest) {
		ModelAndView modelAndView = new ModelAndView("user-receptions");
		Page<ReceptionDTO> receptionDTOs = scheduleService.getUserReceptions(pageRequest, null, currentUser.getId());
		modelAndView.addObject("receptions", receptionDTOs);
		modelAndView.addObject("pageRequest", pageRequest);
		modelAndView.addObject("today", LocalDate.now());
		modelAndView.addObject("now", LocalDateTime.now());

		log.trace("receptions : {}", receptionDTOs.getContent());
		return modelAndView;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/user-receptions")
	public ModelAndView getUserReceptions(
			@PageableDefault Pageable pageRequest,
			@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(name = "user", required = false) Long id) {

		ModelAndView modelAndView = new ModelAndView("user-receptions");
		List<User> users = userService.findAll();
		User user;
		Page<ReceptionDTO> receptionDTOs;

		if (users.isEmpty()) {
			receptionDTOs = new PageImpl<>(Collections.emptyList());
		} else {
			user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(users.get(0));
			receptionDTOs = scheduleService.getUserReceptions(pageRequest, date, user.getId());
		}

		modelAndView.addObject("receptions", receptionDTOs);
		modelAndView.addObject("pageRequest", pageRequest);
		modelAndView.addObject("today", LocalDate.now());
		modelAndView.addObject("now", LocalDateTime.now());

		modelAndView.addObject("users", users);

		log.trace("receptions : {}", receptionDTOs.getContent());
		return modelAndView;
	}
}
