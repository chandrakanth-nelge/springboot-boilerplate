package com.chan.nel.springboot.boilerplate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public enum Role {
	DEFAULT("DEFAULT"), SYSTEM_ADMIN("System Admin");

	@Getter
	private final String name;
}