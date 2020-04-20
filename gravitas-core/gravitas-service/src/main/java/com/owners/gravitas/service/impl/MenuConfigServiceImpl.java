package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.MenuConfig;
import com.owners.gravitas.repository.MenuConfigRepository;
import com.owners.gravitas.service.MenuConfigService;

@Service
public class MenuConfigServiceImpl implements MenuConfigService {

	@Autowired
	private MenuConfigRepository menuConfigRepository;
	
	@Override
	public List<MenuConfig> getMenuConfigOfRole(String roleId) {
		return menuConfigRepository.findByRoleIdAndDisableFalseOrderByOrder(roleId);
	}

	@Override
	public List<MenuConfig> getListOfMenusForAllRole() {
		return menuConfigRepository.findByOrderByRoleIdAndOrderByOrder();
	}

	@Override
	public List<MenuConfig> findAllByDisableFalse() {
		return menuConfigRepository.findAllByDisableFalse();
	}

}
