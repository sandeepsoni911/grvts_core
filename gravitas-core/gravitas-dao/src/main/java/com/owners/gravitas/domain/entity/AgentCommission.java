package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class AgentCommission.
 *
 * @author bhardrah
 */
@Entity( name = "gr_agent_commission" )
public class AgentCommission extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7706243581345858400L;

    /** The state */
    @Column( name = "STATE", nullable = false )
    private String state;

    /** The min value. */
    @Column( name = "MIN_VALUE", nullable = false )
    private int minValue;
    
    /** The self generated percentage. */
    @Column( name = "SELF_GEN_PERCENTAGE", nullable = false )
    private float selfGenPercentage;
    
    /** The non self generated percentage. */
    @Column( name = "NON_SELF_GEN_PERCENTAGE", nullable = false )
    private float nonSelfGenPercentage;
    
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * @return the minValue
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue( int minValue ) {
        this.minValue = minValue;
    }

    /**
     * @return the selfGenPercentage
     */
    public float getSelfGenPercentage() {
        return selfGenPercentage;
    }

    /**
     * @param selfGenPercentage the selfGenPercentage to set
     */
    public void setSelfGenPercentage( float selfGenPercentage ) {
        this.selfGenPercentage = selfGenPercentage;
    }

    /**
     * @return the nonSelfGenPercentage
     */
    public float getNonSelfGenPercentage() {
        return nonSelfGenPercentage;
    }

    /**
     * @param nonSelfGenPercentage the nonSelfGenPercentage to set
     */
    public void setNonSelfGenPercentage( float nonSelfGenPercentage ) {
        this.nonSelfGenPercentage = nonSelfGenPercentage;
    }
}