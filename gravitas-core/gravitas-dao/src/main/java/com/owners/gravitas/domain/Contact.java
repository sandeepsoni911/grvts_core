package com.owners.gravitas.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class Contact.
 *
 * @author amits
 */
@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Contact extends BaseDomain {

    /** For Serialization. */
    private static final long serialVersionUID = -4504630952397145378L;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The phone number. */
    private List< PhoneNumber > phoneNumbers = new ArrayList<>();

    /** The email. */
    private List< String > emails = new ArrayList<>();

    /** The preferred contact time. */
    private String preferredContactTime;

    /** The Preferred contact method. */
    private String preferredContactMethod;

    /** The crm id. */
    private String crmId;

    /** The client id. */
    private String ownersId;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The last modified by. */
    private String lastModifiedBy;

    /** The deleted. */
    private Boolean deleted = Boolean.FALSE;

    /**
     * Adds the phone.
     *
     * @param number
     *            the number
     */
    public void addPhone( final PhoneNumber number ) {
        this.phoneNumbers.add( number );
    }

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
    public void setFirstName( final String firstName ) {
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
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone numbers.
     *
     * @return the phone numbers
     */
    public List< PhoneNumber > getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the phone numbers.
     *
     * @param phoneNumbers
     *            the new phone numbers
     */
    public void setPhoneNumbers( final List< PhoneNumber > phoneNumbers ) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * Gets the emails.
     *
     * @return the emails
     */
    public List< String > getEmails() {
        return emails;
    }

    /**
     * Sets the emails.
     *
     * @param emails
     *            the new emails
     */
    public void setEmails( List< String > emails ) {
        this.emails = emails;
    }

    /**
     * Gets the preferred contact time.
     *
     * @return the preferred contact time
     */
    public String getPreferredContactTime() {
        return preferredContactTime;
    }

    /**
     * Sets the preferred contact time.
     *
     * @param preferredContactTime
     *            the new preferred contact time
     */
    public void setPreferredContactTime( final String preferredContactTime ) {
        this.preferredContactTime = preferredContactTime;
    }

    /**
     * Gets the preferred contact method.
     *
     * @return the preferred contact method
     */
    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }

    /**
     * Sets the preferred contact method.
     *
     * @param preferredContactMethod
     *            the new preferred contact method
     */
    public void setPreferredContactMethod( final String preferredContactMethod ) {
        this.preferredContactMethod = preferredContactMethod;
    }

    /**
     * Adds the email.
     *
     * @param email
     *            the email
     */
    public void addEmail( final String email ) {
        this.emails.add( email );
    }

    /**
     * Gets the crm id.
     *
     * @return the crmId
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the crmId to set
     */
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the client id.
     *
     * @return the clientId
     */
    public String getOwnersId() {
        return ownersId;
    }

    /**
     * Sets the client id.
     *
     * @param ownersId
     *            the clientId to set
     */
    public void setOwnersId( final String ownersId ) {
        this.ownersId = ownersId;
    }

    /**
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy
     *            the new last modified by
     */
    public void setLastModifiedBy( final String lastModifiedBy ) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Gets the deleted.
     *
     * @return the deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted( Boolean deleted ) {
        this.deleted = deleted;
    }
}
