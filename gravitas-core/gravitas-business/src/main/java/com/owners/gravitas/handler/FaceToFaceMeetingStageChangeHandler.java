package com.owners.gravitas.handler;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class FaceToFaceMeetingStageChangeHandler.
 *
 * @author ankusht
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Face To Face Meeting" )
public class FaceToFaceMeetingStageChangeHandler extends OpportunityChangeHandler {

    /** The Constant FEEDBACK_EMAIL_TEMPLATE. */
    private final static String FEEDBACK_EMAIL_TEMPLATE = "agent-interaction-2";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#buildTask(java.lang.
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
        if (feedbackEmailJmxConfig.getFaceToFaceMtgStgFeedbackEnabled()) {
            sendEmail( FEEDBACK_EMAIL_TEMPLATE, agentId, crmOpportunityId, contact );
        }
    }
}
