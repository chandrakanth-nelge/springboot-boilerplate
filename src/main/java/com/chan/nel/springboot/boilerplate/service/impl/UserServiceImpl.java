package com.chan.nel.springboot.boilerplate.service.impl;

import static com.chan.nel.springboot.boilerplate.constant.Role.DEFAULT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.chan.nel.springboot.boilerplate.dao.RoleRepository;
import com.chan.nel.springboot.boilerplate.dao.UserHistoryRepository;
import com.chan.nel.springboot.boilerplate.dao.UserRepository;
import com.chan.nel.springboot.boilerplate.dao.UserRoleRepository;
import com.chan.nel.springboot.boilerplate.dto.CreateUserDto;
import com.chan.nel.springboot.boilerplate.dto.UpdateUserDto;
import com.chan.nel.springboot.boilerplate.dto.UserDto;
import com.chan.nel.springboot.boilerplate.dto.UserRoleDto;
import com.chan.nel.springboot.boilerplate.entity.Role;
import com.chan.nel.springboot.boilerplate.entity.Status;
import com.chan.nel.springboot.boilerplate.entity.User;
import com.chan.nel.springboot.boilerplate.entity.UserHistory;
import com.chan.nel.springboot.boilerplate.entity.UserRole;
import com.chan.nel.springboot.boilerplate.exception.RoleNotFoundException;
import com.chan.nel.springboot.boilerplate.exception.UserNotFoundException;
import com.chan.nel.springboot.boilerplate.modelmapper.RoleMapper;
import com.chan.nel.springboot.boilerplate.modelmapper.UserMapper;
import com.chan.nel.springboot.boilerplate.service.UserService;

/**
 * Implementation of {@link UserService}.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	private final TransactionTemplate transactionTemplate;

	public UserServiceImpl(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@Override
	public UserDto prepareUserDto(User user) {
		UserDto userDto = userMapper.convertToDto(user);

		List<Role> roles = user.getUserRoles().stream()
				.filter(userRole -> !userRole.getRole().getName().equals(DEFAULT.getName())).map(UserRole::getRole)
				.collect(Collectors.toList());
		List<UserRoleDto> roleDtos = roleMapper.convertToUserRoleDtos(roles);
		userDto.setRoles(roleDtos);

		return userDto;
	}

	/**
	 * @param uuid uuid of user
	 * @return UserDto
	 */
	@Override
	@Transactional
	public UserDto getUserByUuid(UUID uuid) {
		Optional<User> userOptional = userRepository.findByUuid(uuid);
		User user = userOptional.orElseThrow(UserNotFoundException::new);
		return prepareUserDto(user);
	}

	@Override
	@Transactional
	public UserDto createUser(CreateUserDto createUserDto) {
		Long createdBy = -1l;

		UserDto userDto = userMapper.convertToDto(createUserDto);
		User user = userMapper.convertToEntity(userDto);
		user.setStatus(Status.CREATED);
		user.setPerformedBy(createdBy);

		List<UserRole> userRoles = createUserDto.getRoles().stream()
				.map(roleUuid -> roleRepository.findByUuid(roleUuid).orElseThrow(RoleNotFoundException::new))
				.map(role -> new UserRole(user, role)).collect(Collectors.toList());

		Role defaultRole = roleRepository.findByNameIgnoreCase(DEFAULT.getName())
				.orElseThrow(RoleNotFoundException::new);
		userRoles.add(new UserRole(user, defaultRole));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				User userPersisted = userRepository.saveAndFlush(user);
				UserHistory userHistory = UserHistory.builder().id(new UserHistory.UserHistoryId(user.getId()))
						.uuid(user.getUuid()).email(user.getEmail()).firstName(user.getFirstName())
						.lastName(user.getLastName()).status(user.getStatus()).performedBy(user.getPerformedBy())
						.build();
				userHistoryRepository.save(userHistory);

				userRoles.forEach(userRole -> userRole.setUser(userPersisted));
				userRoleRepository.saveAll(userRoles);
			}
		});
		return prepareUserDto(user);
	}

	@Override
	@Transactional
	public UserDto updateUser(UpdateUserDto updateUserDto) {
		Long updatedBy = -1l;

		UUID uuid = updateUserDto.getUuid();
		User user = userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);

		UserDto userDto = userMapper.convertToDto(updateUserDto);
		userMapper.mergeToEntity(userDto, user);
		user.setStatus(Status.UPDATED);
		user.setPerformedBy(updatedBy);

		List<UserRole> existingUserRoles = user.getUserRoles();
		List<UserRole> newUserRoles = updateUserDto.getRoles().stream()
				.map(roleUuid -> roleRepository.findByUuid(roleUuid).orElseThrow(RoleNotFoundException::new))
				.map(role -> new UserRole(user, role)).collect(Collectors.toList());

		user.getUserRoles().stream().filter(userRole -> userRole.getRole().getName().equals(DEFAULT.getName()))
				.findFirst().ifPresent(newUserRoles::add);

		List<UserRole> existingUserRolesToDelete = new ArrayList<>(existingUserRoles);
		existingUserRolesToDelete.removeAll(newUserRoles);
		newUserRoles.removeAll(existingUserRoles);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				userRepository.saveAndFlush(user);
				UserHistory userHistory = UserHistory.builder().id(new UserHistory.UserHistoryId(user.getId()))
						.uuid(user.getUuid()).email(user.getEmail()).firstName(user.getFirstName())
						.lastName(user.getLastName()).status(user.getStatus()).performedBy(user.getPerformedBy())
						.build();
				userHistoryRepository.save(userHistory);

				if (!existingUserRolesToDelete.isEmpty()) {
					userRoleRepository.deleteAll(existingUserRolesToDelete);
				}
				if (!newUserRoles.isEmpty()) {
					userRoleRepository.saveAll(newUserRoles);
				}
			}
		});
		return prepareUserDto(user);
	}

	@Override
	@Transactional
	public void deleteUser(UUID uuid) {
		Long deletedBy = -1l;

		User user = userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
		user.setStatus(Status.DELETED);
		user.setPerformedBy(deletedBy);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				userRoleRepository.deleteAll(user.getUserRoles());
				userRepository.delete(user);
				UserHistory userHistory = UserHistory.builder().id(new UserHistory.UserHistoryId(user.getId()))
						.uuid(user.getUuid()).email(user.getEmail()).firstName(user.getFirstName())
						.lastName(user.getLastName()).status(user.getStatus()).performedBy(user.getPerformedBy())
						.build();
				userHistoryRepository.save(userHistory);
			}
		});
	}
}