package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class OpportunityAction.
 *
 * @author raviz
 */
@Entity( name = "GR_OPPORTUNITY_ACTION" )
public class OpportunityAction extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3464647166087554982L;

    /** The action flow id. */
    @Column( name = "FB_ACTION_FLOW_ID", nullable = false )
    private String actionFlowId;

    /** The action id. */
    @Column( name = "FB_ACTION_ID", nullable = false )
    private String actionId;

    /** The opportunity id. */
    @Column( name = "FB_OPPORTUNITY_ID", nullable = false )
    private String opportunityId;

    /** The completed. */
    @Column( name = "COMPLETED", nullable = false )
    private boolean completed;

    /** The deleted. */
    @Column( name = "DELETED", nullable = false )
    private boolean deleted;

    /** The started on. */
    @Column( name = "STARTED_ON" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime startedOn;

    /** The last action visited on. */
    @Column( name = "LAST_ACTION_VISITED_ON" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime lastActionVisitedOn;

    /** The last action updated on. */
    @Column( name = "LAST_ACTION_UPDATED_ON" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime lastActionUpdatedOn;

    /** The action. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "ACTION_ID", nullable = false )
    private Action action;

    /**
     * Instantiates a new opportunity action.
     */
    public OpportunityAction() {

    }

    /**
     * Gets the action flow id.
     *
     * @return the action flow id
     */
    public String getActionFlowId() {
        return actionFlowId;
    }

    /**
     * Sets the action flow id.
     *
     * @param actionFlowId
     *            the new action flow id
     */
    public void setActionFlowId( final String actionFlowId ) {
        this.actionFlowId = actionFlowId;
    }

    /**
     * Gets the action id.
     *
     * @return the action id
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * Sets the action id.
     *
     * @param actionId
     *            the new action id
     */
    public void setActionId( final String actionId ) {
        this.actionId = actionId;
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
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Checks if is completed.
     *
     * @return true, if is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completed.
     *
     * @param completed
     *            the new completed
     */
    public void setCompleted( final boolean completed ) {
        this.completed = completed;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the new deleted
     */
    public void setDeleted( final boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the started on.
     *
     * @return the started on
     */
    public DateTime getStartedOn() {
        return startedOn;
    }

    /**
     * Sets the started on.
     *
     * @param startedOn
     *            the new started on
     */
    public void setStartedOn( final DateTime startedOn ) {
        this.startedOn = startedOn;
    }

    /**
     * Gets the last action visited on.
     *
     * @return the last action visited on
     */
    public DateTime getLastActionVisitedOn() {
        return lastActionVisitedOn;
    }

    /**
     * Sets the last action visited on.
     *
     * @param lastActionVisitedOn
     *            the new last action visited on
     */
    public void setLastActionVisitedOn( final DateTime lastActionVisitedOn ) {
        this.lastActionVisitedOn = lastActionVisitedOn;
    }

    /**
     * Gets the last action updated on.
     *
     * @return the last action updated on
     */
    public DateTime getLastActionUpdatedOn() {
        return lastActionUpdatedOn;
    }

    /**
     * Sets the last action updated on.
     *
     * @param lastActionUpdatedOn
     *            the new last action updated on
     */
    public void setLastActionUpdatedOn( final DateTime lastActionUpdatedOn ) {
        this.lastActionUpdatedOn = lastActionUpdatedOn;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the action.
     *
     * @param action
     *            the new action
     */
    public void setAction( final Action action ) {
        this.action = action;
    }

}
