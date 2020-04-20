package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.AGENT_NOT_FOUND;

/**
 * Exception to denote no agent found for query.
 *
 * @author vishwanathm
 */
public class AgentNotFoundException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = 3811892739995416470L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public AgentNotFoundException( final String message ) {
        super( message, AGENT_NOT_FOUND );
    }

}
