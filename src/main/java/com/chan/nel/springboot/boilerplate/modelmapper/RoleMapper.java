package com.chan.nel.springboot.boilerplate.modelmapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chan.nel.springboot.boilerplate.dto.CreateRoleDto;
import com.chan.nel.springboot.boilerplate.dto.RoleDto;
import com.chan.nel.springboot.boilerplate.dto.UpdateRoleDto;
import com.chan.nel.springboot.boilerplate.dto.UserRoleDto;
import com.chan.nel.springboot.boilerplate.entity.Role;

/**
 * Role Mapper to map {@link RoleDto} to {@link Role} and vice-versa
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Component
public class RoleMapper {

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Convert {@link Role} to {@link UserRoleDto}
	 *
	 * @param role
	 * @return {@link RoleDto}
	 */
	public UserRoleDto convertToUserRoleDto(Role role) {
		return modelMapper.map(role, UserRoleDto.class);
	}

	/**
	 * Convert {@link Role} to {@link RoleDto}
	 *
	 * @param role
	 * @return {@link RoleDto}
	 */
	public RoleDto convertToDto(Role role) {
		return modelMapper.map(role, RoleDto.class);
	}

	/**
	 * Convert {@link RoleDto} to {@link Role}
	 *
	 * @param roleDto
	 * @return {@link Role}
	 */
	public Role convertToEntity(RoleDto roleDto) {
		return modelMapper.map(roleDto, Role.class);
	}

	/**
	 * Convert {@link CreateRoleDto} to {@link Role}
	 *
	 * @param createRoleDto
	 * @return {@link Role}
	 */
	public Role convertToEntity(CreateRoleDto createRoleDto) {
		return modelMapper.map(createRoleDto, Role.class);
	}

	/**
	 * Convert List of {@link Role} to List of {@link UserRoleDto}
	 *
	 * @param roles
	 * @return
	 */
	public List<UserRoleDto> convertToUserRoleDtos(List<Role> roles) {
		return roles.stream().map(role -> convertToUserRoleDto(role)).collect(Collectors.toList());
	}

	/**
	 * Convert List of {@link Role} to List of {@link RoleDto}
	 *
	 * @param roles
	 * @return
	 */
	public List<RoleDto> convertToDtos(List<Role> roles) {
		return roles.stream().map(role -> convertToDto(role)).collect(Collectors.toList());
	}

	/**
	 * Merge {@link RoleDto} with {@link Role}
	 *
	 * @param roleDto
	 * @param role
	 */
	public void mergeToEntity(RoleDto roleDto, Role role) {
		modelMapper.map(roleDto, role);
	}

	/**
	 * Merge {@link UpdateRoleDto} with {@link Role}
	 *
	 * @param updateRoleDto
	 * @param role
	 */
	public void mergeToEntity(UpdateRoleDto updateRoleDto, Role role) {
		modelMapper.map(updateRoleDto, role);
	}

}