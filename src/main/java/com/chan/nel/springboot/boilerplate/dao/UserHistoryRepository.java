package com.chan.nel.springboot.boilerplate.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.UserHistory;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	Optional<UserHistory> findByUuid(UUID uuid);
}