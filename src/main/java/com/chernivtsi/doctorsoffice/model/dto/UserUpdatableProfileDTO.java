package com.chernivtsi.doctorsoffice.model.dto;


import com.chernivtsi.doctorsoffice.constraint.UniqueOnUpdateConstraint;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@UniqueOnUpdateConstraint(targetClass = User.class, checkField = "email", targetField = "email", idField = "id")
public class UserUpdatableProfileDTO {

	private Long id;

	@Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Номер телефону недопустимий. Від 9 до 15 цифр ")
	private String telephone;

	@Valid
	private AddressDTO address;

	@Email(message = "Неправильний формат e-mail")
	@NotBlank(message = "E-mail необхідний")
	private String email;


	public UserUpdatableProfileDTO(User user) {
		this.id = user.getId();
		this.telephone = user.getTelephone();
		this.address = new AddressDTO(user.getAddress());
		this.email = user.getEmail();
	}
}
