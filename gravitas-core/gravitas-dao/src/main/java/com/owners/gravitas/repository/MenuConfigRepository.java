package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.owners.gravitas.domain.entity.MenuConfig;

public interface MenuConfigRepository extends JpaRepository< MenuConfig, String > {

	/**
     * Gets the list of MenuConfig
     *
     * @param roleId
     *            the roleId
     * @return the list of MenuConfig
     */
	public List<MenuConfig> findByRoleIdAndDisableFalseOrderByOrder(String roleId);
	
	@Query("select m from MenuConfig m order by m.roleId, m.order")
	public List<MenuConfig> findByOrderByRoleIdAndOrderByOrder();
	
	public List<MenuConfig> findAllByDisableFalse();
	
}
