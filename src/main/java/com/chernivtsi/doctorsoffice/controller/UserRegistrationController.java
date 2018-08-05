package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.UserRegistrationDTO;
import com.chernivtsi.doctorsoffice.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@lombok.extern.slf4j.Slf4j
public class UserRegistrationController {

	private AccountService accountService;

	public UserRegistrationController(AccountService accountService) {
		this.accountService = accountService;
	}


	@ModelAttribute("user")
	public UserRegistrationDTO userRegistrationDto() {
		return new UserRegistrationDTO();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "authentication/registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDTO userDto,
	                                  BindingResult result, HttpServletRequest request) {
		log.info("Post request from registration page.");
		if (result.hasErrors()) {
			log.trace("Incorrect input in registration form ");
			return "authentication/registration";
		} else {
			accountService.register(userDto, request);
			log.trace("Successful registration");
			return "redirect:/registration?success";
		}
	}

}
