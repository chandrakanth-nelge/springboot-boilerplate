package com.chan.nel.springboot.boilerplate.service.impl;

import static com.chan.nel.springboot.boilerplate.constant.Role.DEFAULT;
import static com.chan.nel.springboot.boilerplate.constant.Role.SYSTEM_ADMIN;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.chan.nel.springboot.boilerplate.dao.ClaimRepository;
import com.chan.nel.springboot.boilerplate.dao.RoleClaimRepository;
import com.chan.nel.springboot.boilerplate.dao.RoleHistoryRepository;
import com.chan.nel.springboot.boilerplate.dao.RoleRepository;
import com.chan.nel.springboot.boilerplate.dao.UserRepository;
import com.chan.nel.springboot.boilerplate.dao.UserRoleRepository;
import com.chan.nel.springboot.boilerplate.dto.ClaimDto;
import com.chan.nel.springboot.boilerplate.dto.CreateRoleDto;
import com.chan.nel.springboot.boilerplate.dto.RoleDto;
import com.chan.nel.springboot.boilerplate.dto.UpdateRoleDto;
import com.chan.nel.springboot.boilerplate.entity.Claim;
import com.chan.nel.springboot.boilerplate.entity.Role;
import com.chan.nel.springboot.boilerplate.entity.RoleClaim;
import com.chan.nel.springboot.boilerplate.entity.RoleHistory;
import com.chan.nel.springboot.boilerplate.entity.Status;
import com.chan.nel.springboot.boilerplate.entity.UserRole;
import com.chan.nel.springboot.boilerplate.exception.ClaimNotFoundException;
import com.chan.nel.springboot.boilerplate.exception.RoleNotFoundException;
import com.chan.nel.springboot.boilerplate.exception.UserManagementException;
import com.chan.nel.springboot.boilerplate.modelmapper.ClaimMapper;
import com.chan.nel.springboot.boilerplate.modelmapper.RoleMapper;
import com.chan.nel.springboot.boilerplate.service.RoleService;
import com.chan.nel.springboot.boilerplate.util.ErrorGenerator;

/**
 * Implementation of {@link RoleService}.
 *
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private RoleClaimRepository roleClaimRepository;

	@Autowired
	private RoleHistoryRepository roleHistoryRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private ClaimMapper claimMapper;

	@Autowired
	private UserRepository userRepository;

	private final TransactionTemplate transactionTemplate;

	public RoleServiceImpl(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@Override
	public List<RoleDto> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		return roles.stream().filter(
				role -> !(role.getName().equals(DEFAULT.getName()) || role.getName().equals(SYSTEM_ADMIN.getName())))
				.map(role -> {
					RoleDto roleDto = roleMapper.convertToDto(role);
					List<Claim> claims = role.getRoleClaims().stream().map(RoleClaim::getClaim)
							.collect(Collectors.toList());
					List<ClaimDto> claimDtos = claimMapper.convertToDtos(claims);
					roleDto.setClaims(claimDtos);
					return roleDto;
				}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public RoleDto createRole(CreateRoleDto createRoleDto) {
		Long createdBy = -1l;

		Role role = roleMapper.convertToEntity(createRoleDto);
		role.setStatus(Status.CREATED);
		role.setPerformedBy(createdBy);

		List<RoleClaim> roleClaims = createRoleDto.getClaims().stream()
				.map(claimUuid -> claimRepository.findByUuid(claimUuid).orElseThrow(ClaimNotFoundException::new))
				.map(claim -> new RoleClaim(role, claim)).collect(Collectors.toList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Role rolePersisted = roleRepository.saveAndFlush(role);
				RoleHistory roleHistory = RoleHistory.builder().id(new RoleHistory.RoleHistoryId(role.getId()))
						.uuid(role.getUuid()).name(role.getName()).status(role.getStatus())
						.performedBy(role.getPerformedBy()).build();
				roleHistoryRepository.save(roleHistory);

				roleClaims.forEach(roleClaim -> roleClaim.setRole(rolePersisted));
				roleClaimRepository.saveAll(roleClaims);
			}
		});
		RoleDto roleDto = roleMapper.convertToDto(role);
		List<ClaimDto> claimDtos = role.getRoleClaims().stream().map(RoleClaim::getClaim)
				.map(claim -> claimMapper.convertToClaimDto(claim)).collect(Collectors.toList());
		roleDto.setClaims(claimDtos);
		return roleDto;
	}

	@Override
	@Transactional
	public RoleDto updateRole(UpdateRoleDto updateRoleDto) {
		Long updatedBy = -1l;

		UUID uuid = updateRoleDto.getUuid();
		Role role = roleRepository.findByUuid(uuid).orElseThrow(RoleNotFoundException::new);

		roleMapper.mergeToEntity(updateRoleDto, role);
		role.setStatus(Status.UPDATED);
		role.setPerformedBy(updatedBy);

		List<RoleClaim> existingRoleClaims = role.getRoleClaims();
		List<RoleClaim> newRoleClaims = updateRoleDto.getClaims().stream()
				.map(claimUuid -> claimRepository.findByUuid(claimUuid).orElseThrow(ClaimNotFoundException::new))
				.map(claim -> new RoleClaim(role, claim)).collect(Collectors.toList());

		List<RoleClaim> existingRoleClaimsToDelete = new ArrayList<>(existingRoleClaims);
		existingRoleClaimsToDelete.removeAll(newRoleClaims);
		newRoleClaims.removeAll(existingRoleClaims);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				roleRepository.saveAndFlush(role);
				RoleHistory roleHistory = RoleHistory.builder().id(new RoleHistory.RoleHistoryId(role.getId()))
						.uuid(role.getUuid()).name(role.getName()).status(role.getStatus())
						.performedBy(role.getPerformedBy()).build();
				roleHistoryRepository.save(roleHistory);

				if (!existingRoleClaimsToDelete.isEmpty()) {
					roleClaimRepository.deleteAll(existingRoleClaimsToDelete);
				}
				if (!newRoleClaims.isEmpty()) {
					roleClaimRepository.saveAll(newRoleClaims);
				}
			}
		});
		RoleDto roleDto = roleMapper.convertToDto(role);
		List<ClaimDto> claimDtos = role.getRoleClaims().stream().map(RoleClaim::getClaim)
				.map(claim -> claimMapper.convertToClaimDto(claim)).collect(Collectors.toList());
		roleDto.setClaims(claimDtos);
		return roleDto;
	}

	@Override
	@Transactional
	public void deleteRole(UUID uuid) {
		Long deletedBy = -1l;

		Role role = roleRepository.findByUuid(uuid).orElseThrow(RoleNotFoundException::new);
		role.setStatus(Status.DELETED);
		role.setPerformedBy(deletedBy);

		List<UserRole> userRoles = userRoleRepository.findByRole(role);
		if (!userRoles.isEmpty()) {
			throw new UserManagementException(ErrorGenerator.generateForCode("1021"));
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				roleClaimRepository.deleteAll(role.getRoleClaims());
				roleRepository.delete(role);
				RoleHistory roleHistory = RoleHistory.builder().id(new RoleHistory.RoleHistoryId(role.getId()))
						.uuid(role.getUuid()).name(role.getName()).status(role.getStatus())
						.performedBy(role.getPerformedBy()).build();
				roleHistoryRepository.save(roleHistory);
			}
		});
	}
}