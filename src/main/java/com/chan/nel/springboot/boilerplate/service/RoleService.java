package com.chan.nel.springboot.boilerplate.service;

import java.util.List;
import java.util.UUID;

import com.chan.nel.springboot.boilerplate.dto.CreateRoleDto;
import com.chan.nel.springboot.boilerplate.dto.RoleDto;
import com.chan.nel.springboot.boilerplate.dto.UpdateRoleDto;

/**
 * Bundles all CRUD APIs for Role.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public interface RoleService {

	List<RoleDto> getAllRoles();

	RoleDto createRole(CreateRoleDto roleDto);

	RoleDto updateRole(UpdateRoleDto roleDto);

	void deleteRole(UUID uuid);
}