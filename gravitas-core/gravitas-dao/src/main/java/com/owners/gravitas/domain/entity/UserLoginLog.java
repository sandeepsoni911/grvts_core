package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class UserLoginLog.
 * 
 * @author pabhishek
 */
@Entity(name = "GR_USER_LOGIN_LOG")
public class UserLoginLog extends AbstractPersistable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -375937036710637257L;

	/** The ip address. */
	@Column(name = "ip_address", nullable = false)
	private String ipAddress;

	/** The operating system. */
	@Column(name = "operating_system", nullable = false)
	private String operatingSystem;

	/** The browser. */
	@Column(name = "browser", nullable = false)
	private String browser;

	/** The created date. */
	@Column(name = "created_on", updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdDate;

	/**
	 * Instantiates a new user ip address.
	 */
	public UserLoginLog() {

	}

	/**
	 * Instantiates a new user ip address.
	 *
	 * @param ipAddress
	 *            the ip address
	 */
	public UserLoginLog(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
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

}
