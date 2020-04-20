package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.AlertDetail;
import com.owners.gravitas.dto.FirstLeadDetail;

/**
 * The Class UserActivityResponse.
 * 
 * @author bhardrah
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserActivityResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4578992308295678281L;
    /** The userId. */
    private String userId;
    /** The email. */
    private String email;
    /** The first name. */
    private String firstName;
    /** The last name. */
    private String lastName;
    /** The phone. */
    private String phone;
    /** The list of alerts. */
    private List< AlertDetail > alertDetails;
    /** The first lead detail. */
    private FirstLeadDetail firstLeadDetail;
    
    /** The boolean to check if lead is claimed. */
    private boolean isLeadClaimed;
    
    /** The opportunity Id */
    private String opportunityId;

    
    
    /**
	 * @return the opportunityId
	 */
	public String getOpportunityId() {
		return opportunityId;
	}

	/**
	 * @param opportunityId the opportunityId to set
	 */
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	/**
     * Gets the userId.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Sets the userId.
     *
     * @param userId
     *            the user Id
     */
    public void setUserId( final String userId ) {
        this.userId = userId;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail( final String email ) {
        this.email = email;
    }
    
    /**
     * Gets the first name.
     *
     * @return the firstName
     */
    
    public String getFirstName() {
        return firstName;
    }
    /**
     * Sets the first name.
     *
     * @param firstName
     *            the first name
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }
    /**
     * Gets the last name.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets the last name.
     *
     * @param lastName
     *            the last name
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
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
     *            the phone
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }
    /**
     * Gets the alert details.
     *
     * @return the alertDetails
     */
    public List< AlertDetail > getAlertDetails() {
        return alertDetails;
    }
    /**
     * Sets the alert details.
     *
     * @param alertDetails
     *            the alert details
     */
    public void setAlertDetails( final List< AlertDetail > alertDetails ) {
        this.alertDetails = alertDetails;
    }
    
    /**
     * @return the firstLeadDetail
     */
    public FirstLeadDetail getFirstLeadDetail() {
        return firstLeadDetail;
    }

    /**
     * @param firstLeadDetail
     *            the firstLeadDetail to set
     */
    public void setFirstLeadDetail( final FirstLeadDetail firstLeadDetail ) {
        this.firstLeadDetail = firstLeadDetail;
    }

    /**
     * @return the isLeadClaimed
     */
    public boolean isLeadClaimed() {
        return isLeadClaimed;
    }

    /**
     * @param isLeadClaimed the isLeadClaimed to set
     */
    public void setLeadClaimed( final boolean isLeadClaimed ) {
        this.isLeadClaimed = isLeadClaimed;
    }
    
}
