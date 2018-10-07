package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Role;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserListDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String patronymic;

	private LocalDate birthDate;

	private Long age;

	private String telephone;

	private List<Role> roles;

	private boolean enabled = false;

	public UserListDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.patronymic = user.getPatronymic();
		this.birthDate = user.getBirthDate();
		this.age = ChronoUnit.YEARS.between(this.getBirthDate(), LocalDate.now());
		this.telephone = user.getTelephone();
		this.roles = user.getRoles();
		this.enabled = user.isEnabled();
	}
}
