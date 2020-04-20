package com.owners.gravitas.handler;

import static com.owners.gravitas.enums.PushNotificationType.NEW_TASK;
import static com.owners.gravitas.enums.TaskType.ASK_PREMIUM_TITLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class WritingAnOfferStageChangeHandler.
 *
 * @author amits
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Writing Offer" )
public class WritingOfferStageChangeHandler extends OpportunityChangeHandler {

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The task title. */
    @Value( "${task.writingAnOffer.title}" )
    private String taskTitle;

    /** The Constant FEEDBACK_EMAIL_TEMPLATE. */
    private final static String FEEDBACK_EMAIL_TEMPLATE = "agent-interaction-docProcessing";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        final PostResponse response = saveTask( agentId, opportunityId, contact, ASK_PREMIUM_TITLE, taskTitle );
        if (contact != null && response != null) {
            final NotificationRequest notificationRequest = new NotificationRequest( contact.getFirstName(),
                    contact.getLastName(), NEW_TASK, response.getName(), null );
            agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#getTask(java.lang.
     * String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public Task buildTask( final String agentId, final String opportunityId, final Contact contact ) {
        return buildTask( agentId, opportunityId, contact, ASK_PREMIUM_TITLE, taskTitle );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail()
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {
        if (feedbackEmailJmxConfig.getWritingOfferStgFeedbackEnabled()) {
            sendEmail( FEEDBACK_EMAIL_TEMPLATE, agentId, crmOpportunityId, contact );
        }
    }
}
