package com.chernivtsi.doctorsoffice.constraint;

import com.chernivtsi.doctorsoffice.constraint.validator.UniqueOnUpdateValidator;
import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks whether field is unique, allowing update of object
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueOnUpdateValidator.class)
public @interface UniqueOnUpdateConstraint {

	String idField() default "";

	String checkField();

	Class<? extends AbstractIdentifiable> targetClass();

	String targetField();

	String message() default "Це значення не є унікальним";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}