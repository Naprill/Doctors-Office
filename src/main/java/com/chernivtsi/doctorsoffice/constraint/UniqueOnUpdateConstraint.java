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

	/**
	 * That is value field that must be unique, located in DTO that will be annotated with this constraint
	 * @return field name
	 */
	String checkField();

	Class<? extends AbstractIdentifiable> targetClass();

	/**
	 * That is value field that is located in entity and corresponds to checkField
	 * @return field name
	 */
	String targetField();

	String message() default "Користувач з таким email вже зареєстрований";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}