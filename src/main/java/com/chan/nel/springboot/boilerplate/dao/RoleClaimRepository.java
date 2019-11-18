package com.chan.nel.springboot.boilerplate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.RoleClaim;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RoleClaimRepository extends JpaRepository<RoleClaim, RoleClaim.RoleClaimId> {
	
}
