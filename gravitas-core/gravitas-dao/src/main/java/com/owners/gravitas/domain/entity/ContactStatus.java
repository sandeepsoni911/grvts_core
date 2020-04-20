package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Lead status entity
 * @author sandeepsoni
 *
 */
@Entity( name = "gr_contact_status" )
public class ContactStatus extends AbstractAuditable {
	
	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The email. */
    @Column( name = "EMAIL", nullable = false )
    private String email;
    
    /** The status. */
    @Column( name = "status")
    private String status;

    /** The request json. */
    @Column( name = "request_json" )
    private String requestJson;

    /** The lead source. */
    @Column( name = "lead_source")
    private String leadSource;
    
    /** The request type. */
    @Column( name = "request_type" )
    private String requestType;
    
    /** The lead type. */
    @Column( name = "lead_type" )
    private String leadType;
    

    /** The status message. */
    @Column( name = "status_message" )
    private String statusMessage;
    
    /** The status message. */
    @Column( name = "retry_count" )
    private Integer retryCount;
    
	/**
	 * @return the retryCount
	 */
	public Integer getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the requestJson
	 */
	public String getRequestJson() {
		return requestJson;
	}

	/**
	 * @param requestJson the requestJson to set
	 */
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	/**
	 * @return the leadSource
	 */
	public String getLeadSource() {
		return leadSource;
	}

	/**
	 * @param leadSource the leadSource to set
	 */
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the leadType
	 */
	public String getLeadType() {
		return leadType;
	}

	/**
	 * @param leadType the leadType to set
	 */
	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	@Override
	public String toString() {
		return "ContactStatus [email=" + email + ", status=" + status + ", requestJson=" + requestJson + ", leadSource="
				+ leadSource + ", requestType=" + requestType + ", leadType=" + leadType + ", statusMessage="
				+ statusMessage + ", retryCount=" + retryCount + "]";
	}
}
