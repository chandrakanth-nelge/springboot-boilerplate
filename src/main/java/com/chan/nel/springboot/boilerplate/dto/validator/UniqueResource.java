package com.chan.nel.springboot.boilerplate.dto.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueResourceValidator.class })
@ReportAsSingleViolation
@Documented
public @interface UniqueResource {

	String message() default "1000";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends ConstraintValidator<?, ?>> constraintValidator();
}