package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.Optional;
import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chan.nel.springboot.boilerplate.dao.UserRepository;
import com.chan.nel.springboot.boilerplate.dto.UpdateUserDto;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class UniqueUserValidator implements ConstraintValidator<UniqueResource, UpdateUserDto> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@SuppressWarnings("squid:S3655")
	public boolean isValid(UpdateUserDto updateUserDto, ConstraintValidatorContext context) {
		UUID uuid = updateUserDto.getUuid();
		Optional<Long> countExistOptional = userRepository.countByUuid(uuid);
		if (countExistOptional.get() < 1) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("1004").addPropertyNode("uuid").addConstraintViolation();
			return false;
		}

		Optional<Long> countEmailOptional = userRepository.countByUuidNotAndEmailIgnoreCase(uuid,
				updateUserDto.getEmail());
		if (countEmailOptional.get() > 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("1061").addPropertyNode("email").addConstraintViolation();
			return false;
		}

		return true;
	}
}