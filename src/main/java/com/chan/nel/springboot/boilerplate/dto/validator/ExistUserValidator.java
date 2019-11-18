package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.Optional;
import java.util.UUID;

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
public class ExistUserValidator implements ConstraintValidator<Exist, UUID> {

	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("squid:S3655")
	@Override
	public boolean isValid(UUID userUuid, ConstraintValidatorContext context) {
		Optional<Long> countOptional = userRepository.countByUuid(userUuid);
		return countOptional.get() == 1;
	}
}