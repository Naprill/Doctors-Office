package com.chernivtsi.doctorsoffice.model.dto;


import com.chernivtsi.doctorsoffice.constraint.UniqueOnUpdateConstraint;
import com.chernivtsi.doctorsoffice.model.Analysis;
import com.chernivtsi.doctorsoffice.model.MedicalExamination;
import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@UniqueOnUpdateConstraint(targetClass = User.class, checkField = "email", targetField = "email", idField = "id")
public class UserProfileDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String patronymic;

	private LocalDate birthDate;

	private Long age;

	@Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Номер телефону недопустимий. Від 9 до 15 цифр ")
	private String telephone;

	@Valid
	private AddressDTO address;

	@Email(message = "Неправильний формат e-mail")
	@NotBlank(message = "E-mail необхідний")
	private String email;

	private LocalDate registrationDate;

	private List<MedicalExamination> medicalExaminations;

	private List<Analysis> analyses;

	private List<Therapy> therapies;

	public UserProfileDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.patronymic = user.getPatronymic();
		this.birthDate = user.getBirthDate();
		this.age = ChronoUnit.YEARS.between(this.getBirthDate(), LocalDate.now());
		this.telephone = user.getTelephone();
		this.address = new AddressDTO(user.getAddress());
		this.email = user.getEmail();
		this.registrationDate = user.getRegistrationDate();
		this.medicalExaminations = user.getMedicalExaminations();
		this.analyses = user.getAnalyses();
		this.therapies = user.getTherapies();
	}
}
