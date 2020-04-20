package com.owners.gravitas.handler;

import static com.owners.gravitas.constants.Constants.PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class PendingSaleStageChangeHandler.
 *
 * @author shivamm
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Pending Sale" )
public class PendingSaleStageChangeHandler extends OpportunityChangeHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PendingSaleStageChangeHandler.class );

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        final String crmId = opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ).getContact().getCrmId();
        LOGGER.info( "In PendingSaleStageChangeHandler.handleChange() crmOppId is " + crmId );
        agentBusinessService.sendPendingSalePTSEmailNotification( PTS.equalsIgnoreCase( getAccountName( crmId ) ),
                getAgentSource( agentId ), getOpportunitySource( crmId ) );
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
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail(
     * java.lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {
        // do nothing
    }
}
