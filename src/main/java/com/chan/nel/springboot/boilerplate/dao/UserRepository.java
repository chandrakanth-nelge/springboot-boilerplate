package com.chan.nel.springboot.boilerplate.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.User;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u.id FROM User u WHERE u.uuid = (:uuid)")
	Optional<Long> findIdByUuid(@Param("uuid") UUID uuid);

	Optional<User> findByUuid(UUID uuid);

	Optional<Long> countByEmailIgnoreCase(String email);

	Optional<Long> countByUuid(UUID uuid);

	Optional<Long> countByUuidNotAndEmailIgnoreCase(UUID uuid, String email);
}