/**
 *
 */
package com.owners.gravitas.dto.response;

import java.util.List;

import com.owners.gravitas.dto.ActionLogDto;

/**
 * The Class ActionLogResponse.
 *
 * @author harshads
 */
public class ActionLogResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6742415397026509883L;

    /** The action logs. */
    private List< ActionLogDto > actionLogs;

    /**
     * Gets the action logs.
     *
     * @return the actionLogs
     */
    public List< ActionLogDto > getActionLogs() {
        return actionLogs;
    }

    /**
     * Sets the action logs.
     *
     * @param actionLogs
     *            the actionLogs to set
     */
    public void setActionLogs( final List< ActionLogDto > actionLogs ) {
        this.actionLogs = actionLogs;
    }

}
