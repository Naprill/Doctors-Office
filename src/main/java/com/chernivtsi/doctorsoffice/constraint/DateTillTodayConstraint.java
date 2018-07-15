package com.chernivtsi.doctorsoffice.constraint;

import com.chernivtsi.doctorsoffice.constraint.validator.DateTillTodayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DateTillTodayValidator.class)
@Documented
public @interface DateTillTodayConstraint {

    String message() default "Будь ласка, виберіть дату з минулого";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
