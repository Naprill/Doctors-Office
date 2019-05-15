package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.UserListDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserSearchDTO;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

	/**
	 * Method provides response for autocomplete AJAX request.
	 * Get list of all users, whose first name or last name contains entered fragment
	 *
	 * @param fragment - search fragment
	 * @return list of found users
	 */
	@ResponseBody
	@GetMapping(value = "/search")
	public ResponseEntity<List<UserSearchDTO>> getUsers(@RequestParam String fragment) {
		log.trace("Autocomplete user search with fragment: {}", fragment);
		return new ResponseEntity<>(userService.findByNameIgnoreCaseContaining(fragment), HttpStatus.OK);
	}

}
