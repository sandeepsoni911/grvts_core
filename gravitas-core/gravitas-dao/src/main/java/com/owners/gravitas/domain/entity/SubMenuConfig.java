package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GR_SUBMENU_CONFIG")
public class SubMenuConfig extends AbstractConfigPersistable {

	private static final long serialVersionUID = -1499206938789404256L;
	
	@Column(name = "SUB_MODULE_NAME", nullable = false)
	private String subModule;
	
	@Column(name = "DISABLE")
	private Boolean disable;
	
	@ManyToOne
    @JoinColumn(name="MODULE_ID", nullable=false)
	private MenuConfig menu;
	
	@Column(name = "ORDER_BY")
	private int order;

	/**
	 * @return the subModule
	 */
	public String getSubModule() {
		return subModule;
	}

	/**
	 * @param subModule the subModule to set
	 */
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	/**
	 * @return the disable
	 */
	public Boolean getDisable() {
		return disable;
	}

	/**
	 * @param disable the disable to set
	 */
	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	

	/**
	 * @return the menu
	 */
	public MenuConfig getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(MenuConfig menu) {
		this.menu = menu;
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

}
