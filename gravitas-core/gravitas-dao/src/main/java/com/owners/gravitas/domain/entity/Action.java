package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class Action.
 *
 * @author shivamm
 */
@Entity( name = "GR_ACTION" )
public class Action extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The crm id. */
    @Column( name = "ACTION_NAME", nullable = false, unique = true )
    private String actionName;

    /** The first name. */
    @Column( name = "ORDER", nullable = false )
    private String order;

    /**
     * Gets the action name.
     *
     * @return the action name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Sets the action name.
     *
     * @param actionName
     *            the new action name
     */
    public void setActionName( String actionName ) {
        this.actionName = actionName;
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
    public void setOrder( String order ) {
        this.order = order;
    }
}
