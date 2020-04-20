package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.RESULT_NOT_FOUND;

/**
 * Exception to denote no results found for query.
 *
 * @author vishwanathm
 */
public class ResultNotFoundException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = 3811892739995416470L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public ResultNotFoundException( final String message ) {
        super( message, RESULT_NOT_FOUND );
    }

}
