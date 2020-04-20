package com.owners.gravitas.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class AgentPerformanceLogId.
 * 
 * @author ankusht
 */
@Embeddable
public class AgentPerformanceLogId implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1674729391645130277L;

	/** The agent email. */
	@Column( name = "AGENT_EMAIL", nullable = false, updatable = false, insertable = false )
	private String agentEmail;

	/** The created date. */
	@Column( name = "CREATED_ON", nullable = false, updatable = false, insertable = false )
	@Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
	private DateTime createdDate;

	/**
	 * Gets the agent email.
	 *
	 * @return the agent email
	 */
	public String getAgentEmail() {
		return agentEmail;
	}

	/**
	 * Sets the agent email.
	 *
	 * @param agentEmail
	 *            the new agent email
	 */
	public void setAgentEmail( final String agentEmail ) {
		this.agentEmail = agentEmail;
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
	public void setCreatedDate( final DateTime createdDate ) {
		this.createdDate = createdDate;
	}

	/**
	 * Instantiates a new agent performance log id.
	 *
	 * @param agentEmail
	 *            the agent email
	 * @param createdDate
	 *            the created date
	 */
	public AgentPerformanceLogId( final String agentEmail, final DateTime createdDate ) {
		super();
		this.agentEmail = agentEmail;
		this.createdDate = createdDate;
	}

	/**
	 * Instantiates a new agent performance log id.
	 */
	public AgentPerformanceLogId() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( agentEmail == null ) ? 0 : agentEmail.hashCode() );
		result = prime * result + ( ( createdDate == null ) ? 0 : createdDate.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentPerformanceLogId other = ( AgentPerformanceLogId ) obj;
		if (agentEmail == null) {
			if (other.agentEmail != null)
				return false;
		} else if (!agentEmail.equals( other.agentEmail ))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals( other.createdDate ))
			return false;
		return true;
	}

}
