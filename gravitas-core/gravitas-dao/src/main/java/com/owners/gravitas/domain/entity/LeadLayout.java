package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GR_LEAD_DISPLAY_LAYOUT")
public class LeadLayout extends AbstractPersistable {

	private static final long serialVersionUID = -1499206938789404256L;
	
	@Column(name = "EMAIL", nullable = false)
	private String emailId;
	
	@Column(name = "LAYOUT")
	private String layout;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "SOURCE")
	private String source;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
