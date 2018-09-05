package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.token.ConfirmAccountToken;
import com.chernivtsi.doctorsoffice.service.AccountTokenService;
import com.chernivtsi.doctorsoffice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/confirm-account")
public class ConfirmAccountController {

	private UserService userService;
	private AccountTokenService accountTokenService;

	public ConfirmAccountController(UserService userService,
	                                AccountTokenService accountTokenService) {
		this.userService = userService;
		this.accountTokenService = accountTokenService;
	}

	@GetMapping
	@Transactional
	public String displayConfirmAccountPage(@RequestParam(required = false) String token, Model model) {

		ConfirmAccountToken accountToken = accountTokenService.findByTokenUrl(token);
		if (accountToken == null) {
			model.addAttribute("error", "Помилка. Не знайдено токену для підтвердження акаунту");
		} else {
			model.addAttribute("success", "Вітаю! Ви успішно підтвердили свій акаунт" +
					" і можете ввійти з своєю електронною поштою та паролем");
			model.addAttribute("url", "/login");
			User user = accountToken.getUser();
			userService.updateUserEnabled(true, user.getId());
			userService.createAnalysesDirectory(user.getId());
			accountTokenService.deleteByToken(accountToken);
		}
		return "layouts/layoutConfirmAccount";
	}

}
