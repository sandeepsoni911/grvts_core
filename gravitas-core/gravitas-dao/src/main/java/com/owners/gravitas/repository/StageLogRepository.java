package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.StageLog;

public interface StageLogRepository extends JpaRepository< StageLog, String > {

}
