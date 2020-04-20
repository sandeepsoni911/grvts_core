package com.owners.gravitas.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ActionGroup.
 *
 * @author shivamm
 */
public class ActionGroup extends BaseDomain {

    /** The actions. */
    private List< Action > actions = new ArrayList<>();

    /** The opportunity id. */
    private String opportunityId;

    /** The gravitas group id. */
    private String gravitasGroupId;

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
    public void setOpportunityId( String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the gravitas group id.
     *
     * @return the gravitas group id
     */
    public String getGravitasGroupId() {
        return gravitasGroupId;
    }

    /**
     * Sets the gravitas group id.
     *
     * @param gravitasGroupId
     *            the new gravitas group id
     */
    public void setGravitasGroupId( String gravitasGroupId ) {
        this.gravitasGroupId = gravitasGroupId;
    }

    public List< Action > getActions() {
        return actions;
    }

    public void setActions( List< Action > actions ) {
        this.actions = actions;
    }
}
