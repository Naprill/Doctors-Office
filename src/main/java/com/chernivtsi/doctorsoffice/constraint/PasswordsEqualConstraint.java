package com.chernivtsi.doctorsoffice.constraint;

import com.chernivtsi.doctorsoffice.constraint.validator.PasswordsEqualConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
@Documented
public @interface PasswordsEqualConstraint {

    String message() default "Паролі не співпадають";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
