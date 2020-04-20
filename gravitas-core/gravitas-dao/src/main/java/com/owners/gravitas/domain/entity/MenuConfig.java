package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "GR_MENU_CONFIG")
public class MenuConfig extends AbstractConfigPersistable {

	private static final long serialVersionUID = -1499206938789404256L;
	
	@Column(name = "MODULE_NAME", nullable = false)
	private String module;
	
	@Column(name = "MODULE_ICON")
	private String moduleIcon;
	
	@Column(name = "MODULE_SELECTOR")
	private String moduleSelector;
	
	@Column(name = "MODULE_REDIRECT")
	private String moduleRedirect;
	
	@OneToMany( mappedBy="menu", fetch=FetchType.LAZY )
	@OrderBy("order ASC")
    private List<SubMenuConfig> subModules;
	
	@Column(name = "ROLE_ID", nullable = false)
	private String roleId;
	
	@Column(name = "ORDER_BY")
	private int order;
	
	@Column(name = "IS_DISABLE")
	private boolean disable;
	
	@Column(name = "MENU_NAME")
	private String menuName;
	
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the moduleIcon
	 */
	public String getModuleIcon() {
		return moduleIcon;
	}

	/**
	 * @param moduleIcon the moduleIcon to set
	 */
	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	/**
	 * @return the moduleSelector
	 */
	public String getModuleSelector() {
		return moduleSelector;
	}

	/**
	 * @param moduleSelector the moduleSelector to set
	 */
	public void setModuleSelector(String moduleSelector) {
		this.moduleSelector = moduleSelector;
	}

	/**
	 * @return the moduleRedirect
	 */
	public String getModuleRedirect() {
		return moduleRedirect;
	}

	/**
	 * @param moduleRedirect the moduleRedirect to set
	 */
	public void setModuleRedirect(String moduleRedirect) {
		this.moduleRedirect = moduleRedirect;
	}

	/**
	 * @return the subModules
	 */
	public List<SubMenuConfig> getSubModules() {
		return subModules;
	}

	/**
	 * @param subModules the subModules to set
	 */
	public void setSubModules(List<SubMenuConfig> subModules) {
		this.subModules = subModules;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the disable
	 */
	public boolean isDisable() {
		return disable;
	}

	/**
	 * @param disable the disable to set
	 */
	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
