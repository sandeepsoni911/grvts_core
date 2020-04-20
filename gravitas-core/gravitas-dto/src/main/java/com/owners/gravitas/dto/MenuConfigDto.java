package com.owners.gravitas.dto;

import java.util.List;

public class MenuConfigDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8803116751616899968L;

    private String module;
	
	private String moduleIcon;
	
	private String moduleSelector;
	
	private String moduleRedirect;
	
	private List<SubMenuConfigDto> subModules;

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
	public List<SubMenuConfigDto> getSubModules() {
		return subModules;
	}

	/**
	 * @param subModules the subModules to set
	 */
	public void setSubModules(List<SubMenuConfigDto> subModules) {
		this.subModules = subModules;
	}
	
}
