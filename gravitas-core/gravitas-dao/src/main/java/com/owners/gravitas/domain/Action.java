package com.owners.gravitas.domain;

/**
 * The Class Action.
 *
 * @author shivamm
 */
public class Action extends BaseDomain {

    /** The name. */
    private String name;

    /** The status. */
    private boolean complete;

    /** The status reason. */
    private String statusReason;

    /** The order. */
    private String order;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The last visited dtm. */
    private Long lastVisitedDtm;

    /**
     * Gets the status reason.
     *
     * @return the status reason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * Sets the status reason.
     *
     * @param statusReason
     *            the new status reason
     */
    public void setStatusReason( final String statusReason ) {
        this.statusReason = statusReason;
    }

    /**
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Gets the order.
     *
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets the order.
     *
     * @param order
     *            the new order
     */
    public void setOrder( final String order ) {
        this.order = order;
    }

    /**
     * Gets the last visited dtm.
     *
     * @return the last visited dtm
     */
    public Long getLastVisitedDtm() {
        return lastVisitedDtm;
    }

    /**
     * Sets the last visited dtm.
     *
     * @param lastVisitedDtm
     *            the new last visited dtm
     */
    public void setLastVisitedDtm( final Long lastVisitedDtm ) {
        this.lastVisitedDtm = lastVisitedDtm;
    }

    /**
     * Checks if is complete.
     *
     * @return true, if is complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Sets the complete.
     *
     * @param complete
     *            the new complete
     */
    public void setComplete( final boolean complete ) {
        this.complete = complete;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName( String name ) {
        this.name = name;
    }
}
