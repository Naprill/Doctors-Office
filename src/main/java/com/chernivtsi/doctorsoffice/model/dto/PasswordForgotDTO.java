package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.constraint.EmailDoesNotExistConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class PasswordForgotDTO {

	@Email(message = "Невірний формат. Введіть дійсний емейл")
	@NotEmpty(message = "Введіть емейл")
	@EmailDoesNotExistConstraint
	private String email;
}
