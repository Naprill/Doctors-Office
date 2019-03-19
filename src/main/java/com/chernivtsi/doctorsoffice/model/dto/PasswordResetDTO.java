package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Password;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

@Setter
@Getter
public class PasswordResetDTO {

	@Valid
	private Password password;

	@NotEmpty
	private String token;
}
