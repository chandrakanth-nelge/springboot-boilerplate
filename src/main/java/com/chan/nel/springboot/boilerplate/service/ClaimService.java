package com.chan.nel.springboot.boilerplate.service;

import java.util.List;

import com.chan.nel.springboot.boilerplate.dto.ClaimDto;

/**
 * Bundles all CRUD APIs for Role.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public interface ClaimService {
	List<ClaimDto> getAllClaims();
}