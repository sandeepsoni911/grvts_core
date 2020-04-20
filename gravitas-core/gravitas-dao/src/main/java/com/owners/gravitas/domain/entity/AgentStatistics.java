
package com.owners.gravitas.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class AgentStatistics.
 *
 * @author raviz
 */
@Entity( name = "GR_AGENT_STATISTICS" )
@EntityListeners( AuditingEntityListener.class )
public class AgentStatistics extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7469532178652463140L;

    /** The agent details. */
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "AGENT_ID" )
    private AgentDetails agentDetails;

    /** The key. */
    @Column( name = "`KEY`", nullable = false )
    private String key;

    /** The value. */
    @Column( name = "VALUE", nullable = false )
    private String value;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", updatable = false, nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /**
     * Instantiates a new agent analytics.
     */
    public AgentStatistics() {

    }

    /**
     * @return the agentDetails
     */
    public AgentDetails getAgentDetails() {
        return agentDetails;
    }

    /**
     * @param agentDetails
     *            the agentDetails to set
     */
    public void setAgentDetails( AgentDetails agentDetails ) {
        this.agentDetails = agentDetails;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the new key
     */
    public void setKey( String key ) {
        this.key = key;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the new value
     */
    public void setValue( String value ) {
        this.value = value;
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
    public void setCreatedDate( DateTime createdDate ) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

}
