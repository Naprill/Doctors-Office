package com.chernivtsi.doctorsoffice.constraint.validator;

import com.chernivtsi.doctorsoffice.constraint.PasswordsEqualConstraint;
import com.chernivtsi.doctorsoffice.model.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Represents validator for {@link PasswordsEqualConstraint}
 */

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Password> {

    private String message;

    @Override
    public void initialize(PasswordsEqualConstraint passwordsEqualConstraint) {
        message = passwordsEqualConstraint.message();
    }

    @Override
    public boolean isValid(Password password, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = password.getUniquePassword().equalsIgnoreCase(password.getConfirmPassword());

        if (!isValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }

}
