package com.owners.gravitas.auth.dto;

import com.amazonaws.auth.policy.Action;

/**
 * The Class LambdaApiAction.
 */
public class LambdaApiAction implements Action {

    /** The action name. */
    private String actionName = "execute-api:Invoke";

    /**
     * Sets the action name.
     *
     * @param actionName
     *            the new action name
     */
    public void setActionName( String actionName ) {
        this.actionName = actionName;
    }

    /*
     * (non-Javadoc)
     * @see com.amazonaws.auth.policy.Action#getActionName()
     */
    public String getActionName() {
        return actionName;
    }
}
