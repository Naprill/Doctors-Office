package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.constraint.DateTillTodayConstraint;
import com.chernivtsi.doctorsoffice.constraint.EmailExistConstraint;
import com.chernivtsi.doctorsoffice.model.Address;
import com.chernivtsi.doctorsoffice.model.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationDto {

//    @NotBlank(message = "Ваше ім'я обов'язкове")
//    @Pattern(regexp = "[А-Яа-я]*", message = "Ім'я містить недопустимі символи")
//    String firstName;
//
//    @NotBlank(message = "Ваше прізвище обов'язкове")
//    @Pattern(regexp = "[А-Яа-я]*", message = "Прізвище містить недопустимі символи")
//    String lastName;
//
//    @Pattern(regexp = "[А-Яа-я]*", message = "Побатькові містить недопустимі символи")
//    String patronymic;
//
//	@NotNull(message = "Дата народження необхідна")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@DateTillTodayConstraint
//	LocalDate birthDate;
//
//    @NotBlank(message = "Ваш номер телефону обов'язковий")
//    @Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Номер телефону недопустимий")
//    String telephone;
//
//	@NotNull(message = "Ваша адреса обов'язкова")
//	String address;

	@Email(message = "Неправильний формат e-mail")
	@NotBlank(message = "E-mail необхідний")
	@EmailExistConstraint
	String email;

	@Valid
    Password password;
}
