package com.owners.gravitas.dto.response;

import java.io.Serializable;

/**
 * The class AgentStatesDetails
 * 
 * @author imranmoh
 *
 */
public class AgentStatesDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2403020573029711847L;

    /** The State Id. */
    private String id;

    /** The State Name. */
    private String name;

    /**
     * Constructor
     * 
     * @param id
     * @param name
     */
    public AgentStatesDetails( final String id, final String name ) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( final String name ) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AgentStatesDetails [id=" + id + ", name=" + name + "]";
    }
}
