package com.chan.nel.springboot.boilerplate.dto.validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chan.nel.springboot.boilerplate.dao.ClaimRepository;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class ExistClaimsValidator implements ConstraintValidator<Exist, List<UUID>> {

	@Autowired
	private ClaimRepository claimRepository;

	@SuppressWarnings("squid:S3655")
	@Override
	public boolean isValid(List<UUID> claimUuids, ConstraintValidatorContext context) {
		if (null == claimUuids || claimUuids.isEmpty()) {
			return true;
		}
		Optional<Long> countOptional = claimRepository.countByUuidIn(claimUuids);
		return countOptional.get() == claimUuids.size();
	}
}