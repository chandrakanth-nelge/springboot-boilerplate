package com.chan.nel.springboot.boilerplate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.Role;
import com.chan.nel.springboot.boilerplate.entity.UserRole;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
	List<UserRole> findByRole(Role role);
}