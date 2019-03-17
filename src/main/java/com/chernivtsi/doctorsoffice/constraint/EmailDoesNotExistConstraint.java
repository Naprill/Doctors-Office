package com.chernivtsi.doctorsoffice.constraint;

import com.chernivtsi.doctorsoffice.constraint.validator.EmailDoesNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents constraint for e-mail not existing in data base
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailDoesNotExistValidator.class)
@Documented
public @interface EmailDoesNotExistConstraint {

	String message() default "We could not find an account for that e-mail address.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
