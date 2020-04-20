package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.AGENT_OPPORTUNITY_NOT_FOUND;

/**
 * The Class AgentOpportunityNotFoundException.
 *
 * @author vishwanathm
 */
public class AgentOpportunityNotFoundException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = 3811892739995416470L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     */
    public AgentOpportunityNotFoundException( final String message ) {
        super( message, AGENT_OPPORTUNITY_NOT_FOUND );
    }
}
