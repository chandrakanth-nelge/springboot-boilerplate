package com.chan.nel.springboot.boilerplate.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class ExistValidator implements ConstraintValidator<Exist, Object> {

	@Autowired
	private ConstraintValidatorFactory constraintValidatorFactory;

	private ConstraintValidator constraintValidator;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Exist constraintAnnotation) {
		constraintValidator = constraintValidatorFactory.getInstance(constraintAnnotation.constraintValidator());
		constraintValidator.initialize(constraintAnnotation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return constraintValidator.isValid(value, context);
	}
}