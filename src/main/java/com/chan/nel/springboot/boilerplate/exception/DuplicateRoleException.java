package com.chan.nel.springboot.boilerplate.exception;

import com.chan.nel.springboot.boilerplate.util.ErrorGenerator;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public class DuplicateRoleException extends UserManagementException {
	private static final long serialVersionUID = 1L;

	public DuplicateRoleException() {
		super(ErrorGenerator.generateForCode("1008"));
	}

}