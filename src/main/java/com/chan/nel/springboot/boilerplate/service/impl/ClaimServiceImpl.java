package com.chan.nel.springboot.boilerplate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.chan.nel.springboot.boilerplate.dao.ClaimRepository;
import com.chan.nel.springboot.boilerplate.dto.ClaimDto;
import com.chan.nel.springboot.boilerplate.entity.Claim;
import com.chan.nel.springboot.boilerplate.modelmapper.ClaimMapper;
import com.chan.nel.springboot.boilerplate.service.ClaimService;

/**
 * Implementation of {@link ClaimService}.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private ClaimMapper claimMapper;

	private final TransactionTemplate transactionTemplate;

	public ClaimServiceImpl(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@Override
	public List<ClaimDto> getAllClaims() {
		List<Claim> claims = claimRepository.findAll();
		return claimMapper.convertToDtos(claims);
	}
}