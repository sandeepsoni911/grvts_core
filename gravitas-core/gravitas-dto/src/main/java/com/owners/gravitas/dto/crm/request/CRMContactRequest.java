package com.owners.gravitas.dto.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class Contact.
 *
 * @author vishwanathm
 */
public class CRMContactRequest extends BaseDTO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5920449159321229537L;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The email. */
	private String email;

	/** The account id. */
	private String accountId;

	/** The phone. */
	private String phone;

	/** The mailing street. */
	private String street;

	/** The mailing city. */
	private String city;

	/** The mailing state. */
	private String state;

	/** The mailing postal code. */
	private String zip;

	/** The record type. */
	private String recordType;

	/** The preferred contact time. */
	private String preferredContactTime;

	/** The preferred contact method. */
	private String preferredContactMethod;

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	@JsonProperty("FirstName")
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	@JsonProperty("LastName")
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	@JsonProperty("AccountId")
	public void setAccountId(final String accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone
	 *            the new phone
	 */
	@JsonProperty("Phone")
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the mailing street.
	 *
	 * @return the mailing street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the mailing street.
	 *
	 * @param mailingStreet
	 *            the new mailing street
	 */
	@JsonProperty("MailingStreet")
	public void setStreet(final String mailingStreet) {
		this.street = mailingStreet;
	}

	/**
	 * Gets the mailing city.
	 *
	 * @return the mailing city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the mailing city.
	 *
	 * @param mailingCity
	 *            the new mailing city
	 */
	@JsonProperty("MailingCity")
	public void setCity(final String mailingCity) {
		this.city = mailingCity;
	}

	/**
	 * Gets the mailing state.
	 *
	 * @return the mailing state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the mailing state.
	 *
	 * @param mailingState
	 *            the new mailing state
	 */
	@JsonProperty("MailingState")
	public void setState(final String mailingState) {
		this.state = mailingState;
	}

	/**
	 * Gets the mailing postal code.
	 *
	 * @return the mailing postal code
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the email to set
	 */
	@JsonProperty("Email")
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Sets the mailing postal code.
	 *
	 * @param mailingPostalCode
	 *            the new mailing postal code
	 */
	@JsonProperty("MailingPostalCode")
	public void setZip(final String mailingPostalCode) {
		this.zip = mailingPostalCode;
	}

	/**
	 * Gets the record type id.
	 *
	 * @return the recordTypeId
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * Sets the record type.
	 *
	 * @param recordType
	 *            the new record type
	 */
	@JsonProperty("RecordTypeId")
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	/**
	 * Gets the preferred contact time.
	 *
	 * @return the preferred contact time
	 */
	@JsonProperty("Preferred_Contact_Time__c")
	public String getPreferredContactTime() {
		return preferredContactTime;
	}

	/**
	 * Sets the preferred contact time.
	 *
	 * @param preferredContactTime
	 *            the new preferred contact time
	 */
	public void setPreferredContactTime(String preferredContactTime) {
		this.preferredContactTime = preferredContactTime;
	}

	/**
	 * Gets the preferred contact method.
	 *
	 * @return the preferred contact method
	 */
	@JsonProperty("Preferred_Contact_Method__c")
	public String getPreferredContactMethod() {
		return preferredContactMethod;
	}

	/**
	 * Sets the preferred contact method.
	 *
	 * @param preferredContactMethod
	 *            the new preferred contact method
	 */
	public void setPreferredContactMethod(String preferredContactMethod) {
		this.preferredContactMethod = preferredContactMethod;
	}

}
