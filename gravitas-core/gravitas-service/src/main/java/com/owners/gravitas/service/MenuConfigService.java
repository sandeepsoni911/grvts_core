package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.MenuConfig;

public interface MenuConfigService {

	/**
     * Get List of MenuConfig of Role.
     *
     * @param roleId
     *            the roleId
     * @return the list of MenuConfig
     */
	public List<MenuConfig> getMenuConfigOfRole(String roleId);
	
	/**
     * Get List of Menus for all role
     *
     * @return the list of Menus
     */
	public List<MenuConfig> getListOfMenusForAllRole();
	
	/**
     * Get List of Role Id for Menu
     *
     * @return the list of role id
     */
	public List<MenuConfig> findAllByDisableFalse();
	
	
}
