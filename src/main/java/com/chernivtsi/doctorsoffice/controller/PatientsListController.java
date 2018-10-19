package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.UserListDTO;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/patients")
public class PatientsListController {

	private UserService userService;

	public PatientsListController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public ModelAndView getPatientsList(@PageableDefault Pageable pageRequest) {
		Page<UserListDTO> page = userService.findAll(pageRequest);
		ModelAndView modelAndView = new ModelAndView("patients");
		modelAndView.addObject("patients", page);
		modelAndView.addObject("pageRequest", pageRequest);
		return modelAndView;
	}
}
