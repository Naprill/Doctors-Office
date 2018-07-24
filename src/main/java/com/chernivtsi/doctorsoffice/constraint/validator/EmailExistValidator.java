package com.chernivtsi.doctorsoffice.constraint.validator;

import com.chernivtsi.doctorsoffice.constraint.EmailExistConstraint;
import com.chernivtsi.doctorsoffice.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Represents validator for {@link EmailExistConstraint}
 */
public class EmailExistValidator implements ConstraintValidator<EmailExistConstraint, String> {

    private String message;
    private UserService userService;

    public EmailExistValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(EmailExistConstraint emailExistConstraint) {
        this.message = emailExistConstraint.message();
    }

    @Override
    public boolean isValid(String mail, ConstraintValidatorContext constraintValidatorContext) {


        boolean isValid = !userService.findUserByEmail(mail).isPresent();

        if (isValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
