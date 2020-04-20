package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.NotificationLog;

public interface NotificationRepository extends JpaRepository< NotificationLog, String > {

}
