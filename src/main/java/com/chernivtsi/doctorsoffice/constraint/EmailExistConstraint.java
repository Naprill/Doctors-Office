package com.chernivtsi.doctorsoffice.constraint;

import com.chernivtsi.doctorsoffice.constraint.validator.EmailExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailExistValidator.class)
@Documented
public @interface EmailExistConstraint {

    String message() default "З даним E-mail вже створено акаунт";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
