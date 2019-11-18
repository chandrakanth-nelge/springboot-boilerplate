package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chan.nel.springboot.boilerplate.dao.UserRepository;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueField, String> {

	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("squid:S3655")
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		Optional<Long> longOptional = userRepository.countByEmailIgnoreCase(email);
		return longOptional.get() == 0;
	}
}