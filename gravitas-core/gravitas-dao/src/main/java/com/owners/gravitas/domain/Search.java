package com.owners.gravitas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Search.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search extends BaseDomain {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3009456695250378578L;

	/** The id. */
	private String id;

	/** The agent id. */
	private String agentId;

	/** The agent contactEmail. */
	private String agentEmail;

	/** The contactEmail. */
	private String contactEmail;

	/** The contact id. */
	private String contactId;

	/** The opportunity id. */
	private String opportunityId;

	/** The crm opportunity id. */
	private String crmOpportunityId;

	/**
	 * Instantiates a new search.
	 *
	 * @param agentId
	 *            the agent id
	 * @param contactId
	 *            the contact id
	 * @param crmOpportunityId
	 *            the crm opportunity id
	 * @param contactEmail
	 *            the email
	 * @param opportunityId
	 *            the opportunity id
	 */
	public Search(final String id, final String agentId, final String agentEmail, final String contactId,
			final String crmOpportunityId, final String contactEmail, final String opportunityId) {
		this.id = id;
		this.agentId = agentId;
		this.agentEmail = agentEmail;
		this.contactId = contactId;
		this.crmOpportunityId = crmOpportunityId;
		this.contactEmail = contactEmail;
		this.opportunityId = opportunityId;
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
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Instantiates a new search.
	 */
	public Search() {
		// do nothing.
	}

	/**
	 * Gets the contactEmail.
	 *
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * Sets the contactEmail.
	 *
	 * @param email
	 *            the new contact email
	 */
	public void setContactEmail(final String email) {
		this.contactEmail = email;
	}

	/**
	 * Gets the agent id.
	 *
	 * @return the agent id
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * Sets the agent id.
	 *
	 * @param agentId
	 *            the new agent id
	 */
	public void setAgentId(final String agentId) {
		this.agentId = agentId;
	}

	/**
	 * Gets the opportunity id.
	 *
	 * @return the opportunity id
	 */
	public String getOpportunityId() {
		return opportunityId;
	}

	/**
	 * Sets the opportunity id.
	 *
	 * @param opportunityId
	 *            the new opportunity id
	 */
	public void setOpportunityId(final String opportunityId) {
		this.opportunityId = opportunityId;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(final String contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the crm opportunity id.
	 *
	 * @return the crm opportunity id
	 */
	public String getCrmOpportunityId() {
		return crmOpportunityId;
	}

	/**
	 * Sets the crm opportunity id.
	 *
	 * @param crmOpportunityId
	 *            the new crm opportunity id
	 */
	public void setCrmOpportunityId(final String crmOpportunityId) {
		this.crmOpportunityId = crmOpportunityId;
	}

	/**
	 * Gets the agent email.
	 *
	 * @return the agentEmail
	 */
	public String getAgentEmail() {
		return agentEmail;
	}

	/**
	 * Sets the agent email.
	 *
	 * @param agentEmail
	 *            the agentEmail to set
	 */
	public void setAgentEmail(final String agentEmail) {
		this.agentEmail = agentEmail;
	}
}
