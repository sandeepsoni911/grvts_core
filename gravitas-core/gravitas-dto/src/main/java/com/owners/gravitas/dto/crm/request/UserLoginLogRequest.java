package com.owners.gravitas.dto.crm.request;

import org.joda.time.DateTime;

/**
 * The Class UserLoginLogRequest.
 * 
 * @author pabhishek
 */
public class UserLoginLogRequest {

	/** The id. */
	private String id;

	/** The ip address. */
	private String ipAddress;

	/** The operating system. */
	private String operatingSystem;

	/** The browser. */
	private String browser;

	/** The created date. */
	private DateTime createdDate;

	/**
	 * Instantiates a new user ip address request.
	 */
	public UserLoginLogRequest() {

	}

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress
	 *            the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the operating system.
	 *
	 * @return the operating system
	 */
	public String getOperatingSystem() {
		return operatingSystem;
	}

	/**
	 * Sets the operating system.
	 *
	 * @param operatingSystem
	 *            the new operating system
	 */
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	/**
	 * Gets the browser.
	 *
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Sets the browser.
	 *
	 * @param browser
	 *            the new browser
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public DateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
