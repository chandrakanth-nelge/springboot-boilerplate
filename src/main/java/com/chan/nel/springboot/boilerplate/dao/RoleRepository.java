package com.chan.nel.springboot.boilerplate.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.Role;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByUuid(UUID uuid);

	Optional<Role> findByNameIgnoreCase(String name);

	List<Role> findAllByUuidIn(Iterable<UUID> uuids);

	Optional<Long> countByNameIgnoreCase(String name);

	Optional<Long> countByUuid(UUID uuid);

	Optional<Long> countByUuidNotAndNameIgnoreCase(UUID uuid, String name);

	List<Role> findByNameIn(List<String> names);
}