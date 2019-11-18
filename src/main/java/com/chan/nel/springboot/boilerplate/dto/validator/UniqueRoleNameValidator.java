package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.Optional;

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
public class UniqueRoleNameValidator implements ConstraintValidator<UniqueField, String> {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@SuppressWarnings("squid:S3655")
	public boolean isValid(String roleName, ConstraintValidatorContext context) {
		Optional<Long> longOptional = roleRepository.countByNameIgnoreCase(roleName);
		return longOptional.get() == 0;
	}
}