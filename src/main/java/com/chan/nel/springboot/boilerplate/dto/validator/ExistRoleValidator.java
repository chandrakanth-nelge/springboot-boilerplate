package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.Optional;
import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chan.nel.springboot.boilerplate.dao.RoleRepository;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class ExistRoleValidator implements ConstraintValidator<Exist, UUID> {

	@Autowired
	private RoleRepository roleRepository;

	@SuppressWarnings("squid:S3655")
	@Override
	public boolean isValid(UUID roleUuid, ConstraintValidatorContext context) {
		Optional<Long> countOptional = roleRepository.countByUuid(roleUuid);
		return countOptional.get() == 1;
	}
}