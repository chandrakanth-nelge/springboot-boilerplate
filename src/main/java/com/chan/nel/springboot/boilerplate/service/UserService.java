package com.chan.nel.springboot.boilerplate.service;

import java.util.UUID;

import com.chan.nel.springboot.boilerplate.dto.CreateUserDto;
import com.chan.nel.springboot.boilerplate.dto.UpdateUserDto;
import com.chan.nel.springboot.boilerplate.dto.UserDto;
import com.chan.nel.springboot.boilerplate.entity.User;

/**
 * Bundles all CRUD APIs for User.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public interface UserService {

	UserDto prepareUserDto(User user);

	UserDto getUserByUuid(UUID uuid);

	UserDto createUser(CreateUserDto userDto);

	UserDto updateUser(UpdateUserDto userDto);

	void deleteUser(UUID uuid);
}