package com.owners.gravitas.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.Group;

/**
 * The Class UserGroupRequest.
 *
 * @author raviz
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserGroupRequest extends Group {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5014078691576434174L;

    /** The agent emails. */
    private List< String > userEmails = new ArrayList<>();

    /**
     * Gets the user emails.
     *
     * @return the user emails
     */
    public List< String > getUserEmails() {
        return userEmails;
    }

    /**
     * Sets the user emails.
     *
     * @param userEmails
     *            the new user emails
     */
    public void setUserEmails( final List< String > userEmails ) {
        this.userEmails = userEmails;
    }

}
