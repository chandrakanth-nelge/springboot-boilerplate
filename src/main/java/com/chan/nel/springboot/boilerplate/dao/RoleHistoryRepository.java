package com.chan.nel.springboot.boilerplate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chan.nel.springboot.boilerplate.entity.RoleHistory;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RoleHistoryRepository extends JpaRepository<RoleHistory, Long> {

}