package com.owners.gravitas.handler;

import static com.owners.gravitas.enums.TaskType.CONTACT_OPPORTUNITY;
import static com.owners.gravitas.enums.TaskType.SCHEDULE_MEETING;

import org.springframework.beans.factory.annotation.Value;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * OpportunityChangeHandler to handle "In Contact" Stage.
 *
 * @author vishwanathm
 *
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "In Contact" )
public class InContactStageChangeHandler extends OpportunityChangeHandler {

    /** The task title. */
    @Value( "${task.inContact.title}" )
    private String taskTitle;

    /** The Constant FEEDBACK_EMAIL_TEMPLATE. */
    private final static String FEEDBACK_EMAIL_TEMPLATE = "agent-interaction-1";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        closePreviousStageTask( agentId, opportunityId, CONTACT_OPPORTUNITY );
        return saveTask( agentId, opportunityId, contact, SCHEDULE_MEETING, taskTitle );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#getTask(java.lang.
     * String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public Task buildTask( final String agentId, final String opportunityId, final Contact contact ) {
        return buildTask( agentId, opportunityId, contact, SCHEDULE_MEETING, taskTitle );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail()
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {
        if (feedbackEmailJmxConfig.getInContactStgFeedbackEnabled()) {
            sendEmail( FEEDBACK_EMAIL_TEMPLATE, agentId, crmOpportunityId, contact );
        }
    }
}
