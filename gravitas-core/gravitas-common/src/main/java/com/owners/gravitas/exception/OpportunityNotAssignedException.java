package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.OPPORTUNITY_ASSIGMENT_ERROR;;

/**
 * Exception to denote no agent found for query.
 *
 * @author vishwanathm
 */
public class OpportunityNotAssignedException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = 3811892739995416470L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public OpportunityNotAssignedException( final String message ) {
        super( message, OPPORTUNITY_ASSIGMENT_ERROR );
    }

}
