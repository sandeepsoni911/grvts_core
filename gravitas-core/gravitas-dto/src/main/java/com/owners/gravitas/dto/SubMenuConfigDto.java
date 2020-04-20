package com.owners.gravitas.dto;

public class SubMenuConfigDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8803116751616899968L;

    private String module;
	
	private boolean disable;

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
	
}
