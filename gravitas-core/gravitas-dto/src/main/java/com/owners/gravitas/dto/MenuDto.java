package com.owners.gravitas.dto;

public class MenuDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8803116751616899968L;

    private String name;
    
    private boolean disable;
    
	public MenuDto() {
		super();
	}

	public MenuDto(String name, boolean disable) {
		super();
		this.name = name;
		this.disable = disable;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
