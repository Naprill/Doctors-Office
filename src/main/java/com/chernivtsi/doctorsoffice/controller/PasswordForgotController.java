package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.PasswordForgotDTO;
import com.chernivtsi.doctorsoffice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

	private AccountService accountService;

	public PasswordForgotController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public String displayForgotPasswordPage() {
		return "authentication/forgot-password";
	}

	@ModelAttribute("forgotPasswordForm")
	public PasswordForgotDTO getForgotPasswordDTO(){
		return new PasswordForgotDTO();
	}

	@PostMapping
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDTO forgotPasswordForm,
	                                        BindingResult result,
	                                        HttpServletRequest request) {
		if (result.hasErrors()) {
			return "authentication/forgot-password";
		} else {
			accountService.sendForgotPasswordEmail(forgotPasswordForm.getEmail(),
					accountService.getExpirationTokenProperties().getResetPassword(),
					request);
			log.trace("Successfully sent email for password resetting to user");
			return "redirect:/forgot-password?success";
		}
	}
}
