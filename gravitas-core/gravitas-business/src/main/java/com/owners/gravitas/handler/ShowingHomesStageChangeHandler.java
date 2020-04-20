/**
 *
 */
package com.owners.gravitas.handler;

import static com.owners.gravitas.enums.TaskType.SCHEDULE_MEETING;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class ShowingHomesStageChangeHandler.
 *
 * @author harshads
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Showing Homes" )
public class ShowingHomesStageChangeHandler extends OpportunityChangeHandler {

    /** The Constant FEEDBACK_EMAIL_TEMPLATE. */
    private final static String FEEDBACK_EMAIL_TEMPLATE = "agent-interaction-homeVisit";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        closePreviousStageTask( agentId, opportunityId, SCHEDULE_MEETING );
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#getTask(java.lang.
     * String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public Task buildTask( final String agentId, final String opportunityId, final Contact contact ) {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail()
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {
        if (feedbackEmailJmxConfig.getShowingHomesStgFeedbackEnabled()) {
            sendEmail( FEEDBACK_EMAIL_TEMPLATE, agentId, crmOpportunityId, contact );
        }
    }
}
