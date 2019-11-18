package com.chan.nel.springboot.boilerplate.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.Claim;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
	Optional<Claim> findByUuid(UUID uuid);

	Optional<Long> countByUuid(UUID uuid);

	Optional<Long> countByUuidIn(List<UUID> uuids);
}