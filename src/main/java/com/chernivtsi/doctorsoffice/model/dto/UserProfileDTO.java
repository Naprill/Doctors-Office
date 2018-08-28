package com.chernivtsi.doctorsoffice.model.dto;


import com.chernivtsi.doctorsoffice.constraint.EmailExistConstraint;
import com.chernivtsi.doctorsoffice.model.Address;
import com.chernivtsi.doctorsoffice.model.Analysis;
import com.chernivtsi.doctorsoffice.model.MedicalExamination;
import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@ToString
public class UserProfileDTO {

	private String firstName;

	private String lastName;

	private String patronymic;

	private LocalDate birthDate;

	private Long age;

	@Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Номер телефону недопустимий. Від 9 до 15 цифр ")
	private String telephone;

	@Valid
	private Address address;

	@Email(message = "Неправильний формат e-mail")
	@NotBlank(message = "E-mail необхідний")
	@EmailExistConstraint
	private String email;

	private LocalDate registrationDate;

	private List<MedicalExamination> medicalExaminations;

	private List<Analysis> analyses;

	private List<Therapy> therapies;

	public UserProfileDTO(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.patronymic = user.getPatronymic();
		this.birthDate = user.getBirthDate();
		this.age = ChronoUnit.YEARS.between(this.getBirthDate(), LocalDate.now());
		this.telephone = user.getTelephone();
		this.address = user.getAddress();
		this.email = user.getEmail();
		this.registrationDate = user.getRegistrationDate();
		this.medicalExaminations = user.getMedicalExaminations();
		this.analyses = user.getAnalyses();
		this.therapies = user.getTherapies();
	}
}
