package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.UserLoginLog;

/**
 * The Interface UserLoginLogRepository.
 * 
 * @author pabhishek
 */
public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, String> {

}
