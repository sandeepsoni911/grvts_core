package com.owners.gravitas.handler;

import static com.owners.gravitas.constants.Constants.PTS;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.ClientFollowUpEmailNotificationBuilder;
import com.owners.gravitas.config.ClientFollowupConfig;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class SoldStageChangeHandler.
 *
 * @author raviz
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Sold" )
public class SoldStageChangeHandler extends OpportunityChangeHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SoldStageChangeHandler.class );

    /** The client follow up email notification builder. */
    @Autowired
    private ClientFollowUpEmailNotificationBuilder clientFollowUpEmailNotificationBuilder;

    /** The client followup config. */
    @Autowired
    private ClientFollowupConfig clientFollowupConfig;

    /** The Constant FEEDBACK_EMAIL_TEMPLATE. */
    private final static String FEEDBACK_EMAIL_TEMPLATE = "agent-interaction-overall";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        final String crmId = opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ).getContact().getCrmId();
        LOGGER.info( "In SoldStageChangeHandler.handleChange() crmOppId is " + crmId );
        final OpportunitySource opportunitySource = getOpportunitySource( crmId );
        sendFollowupEmailToClient( opportunitySource );
        agentBusinessService.sendSoldStagePTSEmailNotifications( PTS.equalsIgnoreCase( getAccountName( crmId ) ),
                getAgentSource( agentId ), opportunitySource );
        return null;
    }

    /**
     * Send followup email to client.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param opportunityType
     *            the opportunity type
     */
    private void sendFollowupEmailToClient( final OpportunitySource opportunitySource ) {
        final String opportunityType = opportunitySource.getOpportunityType();
        if (( BUYER.getType().equalsIgnoreCase( opportunityType )
                && clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() )
                || ( SELLER.getType().equalsIgnoreCase( opportunityType )
                        && clientFollowupConfig.isEnableClientFollowUpEmailForSeller() )) {
            LOGGER.info( "sending followup email to end client" );
            mailService.send( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) );
        }
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
        if (feedbackEmailJmxConfig.getSoldStgFeedbackEnabled()) {
            sendEmail( FEEDBACK_EMAIL_TEMPLATE, agentId, crmOpportunityId, contact );
        }
    }
}
