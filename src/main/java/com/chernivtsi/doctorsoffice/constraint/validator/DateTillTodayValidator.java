package com.chernivtsi.doctorsoffice.constraint.validator;

import com.chernivtsi.doctorsoffice.constraint.DateTillTodayConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Represents validator for {@link DateTillTodayConstraint}
 */
public class DateTillTodayValidator implements ConstraintValidator<DateTillTodayConstraint, LocalDate> {

    private String message;

    @Override
    public void initialize(DateTillTodayConstraint dateTillTodayConstraint) {
        this.message = dateTillTodayConstraint.message();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;

        if (localDate != null) {
            isValid = localDate.isBefore(LocalDate.now()) || localDate.equals(LocalDate.now());
        }

        if (!isValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
