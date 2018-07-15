package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.constraint.PasswordsEqualConstraint;
import com.chernivtsi.doctorsoffice.model.dto.UserRegistrationDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

/**
 * Represents {@link UserRegistrationDto} password
 */
@Getter
@Setter
@PasswordsEqualConstraint
public class Password {

    @Pattern(regexp = ".{3,16}", message = "Пароль повинен мати щонайменше 3 символи. ")
    String uniquePassword;

    String confirmPassword;

}
