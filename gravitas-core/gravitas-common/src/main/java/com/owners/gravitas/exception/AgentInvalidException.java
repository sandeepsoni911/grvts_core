package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.AGENT_NOT_VALID;

/**
 * Exception to denote invalid agent.
 *
 * @author manishd
 */
public class AgentInvalidException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = 3811892739995416470L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public AgentInvalidException( final String message ) {
        super( message, AGENT_NOT_VALID );
    }

}
