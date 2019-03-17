package com.chernivtsi.doctorsoffice.constraint.validator;

import com.chernivtsi.doctorsoffice.constraint.EmailDoesNotExistConstraint;
import com.chernivtsi.doctorsoffice.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Represents validator for {@link EmailDoesNotExistConstraint} implements {@link ConstraintValidator}
 */
public class EmailDoesNotExistValidator implements ConstraintValidator<EmailDoesNotExistConstraint, String> {

	private String message;
	private UserService userService;

	public EmailDoesNotExistValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void initialize(EmailDoesNotExistConstraint emailDoesNotExistConstraint) {
		this.message = emailDoesNotExistConstraint.message();
	}

	/**
	 * @param mail
	 * @param constraintValidatorContext
	 * @return find in DB user with specific email and check for false(if didn't not find it)
	 */
	@Override
	public boolean isValid(String mail, ConstraintValidatorContext constraintValidatorContext) {

		boolean isValid = true;

		if (!mail.equals("")) {
			isValid = userService.findUserByEmail(mail).isPresent();
		}

		if (!isValid) {
			constraintValidatorContext.buildConstraintViolationWithTemplate(message)
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
		}

		return isValid;
	}
}
