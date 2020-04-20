/**
 *
 */
package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.User;

/**
 * The Class UserDetailsResponse.
 *
 * @author harshads
 */
public class UserDetailsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3862630594849526651L;

    /** The users. */
    private List< User > users = new ArrayList<>();

    /**
     * Gets the users.
     *
     * @return the users
     */
    public List< User > getUsers() {
        return users;
    }

    /**
     * Sets the users.
     *
     * @param users
     *            the users to set
     */
    public void setUsers( List< User > users ) {
        this.users = users;
    }
}
