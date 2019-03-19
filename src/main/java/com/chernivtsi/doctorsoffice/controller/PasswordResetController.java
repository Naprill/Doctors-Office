package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.PasswordResetDTO;
import com.chernivtsi.doctorsoffice.model.token.AccountToken;
import com.chernivtsi.doctorsoffice.service.AccountTokenService;
import com.chernivtsi.doctorsoffice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {


	private UserService userService;
	private AccountTokenService accountTokenService;
	private PasswordEncoder passwordEncoder;


	public PasswordResetController(UserService userService,
	                               AccountTokenService accountTokenService,
	                               PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.accountTokenService = accountTokenService;
		this.passwordEncoder = passwordEncoder;
	}

	@ModelAttribute("passwordResetForm")
	public PasswordResetDTO passwordReset() {
		return new PasswordResetDTO();
	}

	@GetMapping
	public String displayResetPasswordPage(@RequestParam(required = false) String token,
	                                       Model model) {

		AccountToken resetToken = accountTokenService.findByTokenUrl(token);
		if (resetToken == null) {
			model.addAttribute("error",
					"Токен не знайдено, будь ласка, створіть новий запит на скидання паролю");
		} else if (resetToken.isExpired()) {
			model.addAttribute("error",
					"Термін закінчився, будь ласка, створіть новий запит на скидання паролю");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}
		return "authentication/reset-password";
	}

	@PostMapping
	@Transactional
	public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDTO form,
	                                  BindingResult result,
	                                  RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
			redirectAttributes.addFlashAttribute("passwordResetForm", form);
			return "redirect:/reset-password?token=" + form.getToken();
		}

		AccountToken token = accountTokenService.findByTokenUrl(form.getToken());
		User user = token.getUser();
		String updatedPassword = passwordEncoder.encode(form.getPassword().getUniquePassword());
		userService.updatePassword(updatedPassword, user.getId());
		accountTokenService.deleteByToken(token);

		return "redirect:/login?success";
	}

}
