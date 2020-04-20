/** UserNotLoggedInException.java. */
package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.USER_LOGIN_ERROR;

/**
 * This class represent the case: user not found.
 */
public class UserNotLoggedInException extends ApplicationException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 6426603674969673788L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public UserNotLoggedInException( final String message ) {
        super( message, USER_LOGIN_ERROR );
    }

}
