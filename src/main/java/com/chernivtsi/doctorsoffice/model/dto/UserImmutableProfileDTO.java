package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Analysis;
import com.chernivtsi.doctorsoffice.model.MedicalExamination;
import com.chernivtsi.doctorsoffice.model.Therapy;
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
public class UserImmutableProfileDTO {

	private String firstName;

	private String lastName;

	private String patronymic;

	private LocalDate birthDate;

	private Long age;

	private LocalDate registrationDate;

	private List<MedicalExamination> medicalExaminations;

	private List<Analysis> analyses;

	private List<Therapy> therapies;

	public UserImmutableProfileDTO(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.patronymic = user.getPatronymic();
		this.birthDate = user.getBirthDate();
		this.age = ChronoUnit.YEARS.between(this.getBirthDate(), LocalDate.now());
		this.registrationDate = user.getRegistrationDate();
		this.medicalExaminations = user.getMedicalExaminations();
		this.analyses = user.getAnalyses();
		this.therapies = user.getTherapies();
	}

}
