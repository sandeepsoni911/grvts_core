package com.owners.gravitas.handler;

import static com.owners.gravitas.enums.TaskType.CLAIM_OPPORTUNITY;

import org.springframework.beans.factory.annotation.Value;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * OpportunityChangeHandler to handle "New" Stage.
 *
 * @author Khanujal
 *
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "New" )
public class NewStageChangeHandler extends OpportunityChangeHandler {

    /** The task title. */
    @Value( "${task.new.title}" )
    private String taskTitle;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        return saveTask( agentId, opportunityId, contact, CLAIM_OPPORTUNITY, taskTitle );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#getTask(java.lang.
     * String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public Task buildTask( final String agentId, final String opportunityId, final Contact contact ) {
        return buildTask( agentId, opportunityId, contact, CLAIM_OPPORTUNITY, taskTitle );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail()
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {
        // do nothing
    }
}
