package com.owners.gravitas.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class AgentCoverage.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_AGENT_COVERAGE" )
public class AgentCoverage extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -660467641739017272L;

    /** The zip. */
    @Column( name = "ZIP", nullable = false )
    private String zip;

    /** The servable. */
    @Column( name = "SERVABLE", nullable = false )
    private boolean servable;

    /** The type. */
    @Column( name = "TYPE", nullable = true )
    private String type;

    /** The user. */
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "AGENT_DETAIL_ID", insertable=false, updatable=false )
    private AgentDetails agentDetails;
    
    /**
     * Instantiates a new agent coverage.
     */
    public AgentCoverage() {

    }

    /**
     * Instantiates a new agent coverage.
     *
     * @param zip
     *            the zip
     * @param servable
     *            the servable
     * @param type
     *            the type
     */
    public AgentCoverage( String zip, boolean servable, String type ) {
        super();
        this.zip = zip;
        this.servable = servable;
        this.type = type;
    }

    /**
     * Gets the zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     *
     * @param zip
     *            the new zip
     */
    public void setZip( String zip ) {
        this.zip = zip;
    }

    /**
     * Checks if is servable.
     *
     * @return true, if is servable
     */
    public boolean isServable() {
        return servable;
    }

    /**
     * Sets the servable.
     *
     * @param servable
     *            the new servable
     */
    public void setServable( boolean servable ) {
        this.servable = servable;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( String type ) {
        this.type = type;
    }

	public AgentDetails getAgentDetails() {
		return agentDetails;
	}

	public void setAgentDetails(AgentDetails agentDetails) {
		this.agentDetails = agentDetails;
	}

    
}
