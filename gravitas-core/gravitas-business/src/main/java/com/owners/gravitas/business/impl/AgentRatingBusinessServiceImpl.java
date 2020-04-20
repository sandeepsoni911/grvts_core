package com.owners.gravitas.business.impl;

import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING_ID;
import static com.owners.gravitas.constants.Constants.SPACE;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.ErrorCode.INVALID_RATING_ID_RECEIVED;
import static com.owners.gravitas.util.EncryptDecryptUtil.decrypt;
import static java.lang.Integer.parseInt;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.builder.FeedbackEmailNotificationBuilder;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.FeedbackRequest;
import com.owners.gravitas.dto.response.AgentRatingResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentRatingService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.validator.FeedbackValidator;

/**
 * The Class AgentRatingBusinessServiceImpl.
 *
 * @author ankusht
 */
@Service
public class AgentRatingBusinessServiceImpl implements AgentRatingBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentRatingBusinessServiceImpl.class );

    /** The agent rating service. */
    @Autowired
    private AgentRatingService agentRatingService;

    /** The feedback validator. */
    @Autowired
    private FeedbackValidator feedbackValidator;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The feedback email notification builder. */
    @Autowired
    private FeedbackEmailNotificationBuilder feedbackEmailNotificationBuilder;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentRatingBusinessService#saveAgentRating(
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public AgentRatingResponse saveAgentRating( final String a, final String b ) {
        final Map< String, String > decryptedVals = decrypt( a, b );
        final String ratingId = decryptedVals.get( RATING_ID );
        final Integer rating = parseInt( decryptedVals.get( RATING ) );
        LOGGER.info( "Feedback id received is: " + ratingId + " and rating is: " + rating );
        AgentRating agentRating = agentRatingService.findOne( ratingId );
        if (agentRating != null) {
            feedbackValidator.validateMaxAttempts( agentRating.getFeedbackReceivedCount() );
            feedbackValidator.checkEmailExpiry( agentRating );
            feedbackValidator.validateRating( rating );
            LOGGER.info( "Saving feedback for client: " + agentRating.getClientEmail() + ", agent: "
                    + agentRating.getAgentDetails().getUser().getEmail() );
            agentRating.setRating( rating );
            agentRating.setFeedbackReceivedCount( agentRating.getFeedbackReceivedCount() + 1 );
            agentRating = agentRatingService.save( agentRating );
            return getAgentRatingResponse( agentRating );
        } else {
            throw new ApplicationException( INVALID_RATING_ID_RECEIVED.getErrorDetail() + SPACE + ratingId,
                    INVALID_RATING_ID_RECEIVED );
        }
    }

    /**
     * Gets the agent rating response.
     *
     * @param agentRating
     *            the agent rating
     * @return the agent rating response
     */
    private AgentRatingResponse getAgentRatingResponse( final AgentRating agentRating ) {
        final AgentRatingResponse response = new AgentRatingResponse();
        response.setId( agentRating.getId() );
        response.setAgentEmail( agentRating.getAgentDetails().getUser().getEmail() );
        response.setComments( agentRating.getComments() );
        response.setFeedback( agentRating.getFeedback() );
        response.setFeedbackReceivedCount( agentRating.getFeedbackReceivedCount() );
        response.setRating( agentRating.getRating() );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentRatingBusinessService#sendEmail(java.
     * lang.String, java.lang.String, java.lang.String,
     * com.owners.gravitas.dto.Contact)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void sendEmail( final String emailTemplate, final String agentId, final String crmOpportunityId,
            final Contact contact ) {
        final String recipient = contact.getEmails().get( 0 );
        final String agentEmail = getAgentEmail( agentId );
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( agentEmail );
        final Opportunity opportunity = opportunityService.findOpportunityByCrmId( crmOpportunityId );
        final com.owners.gravitas.domain.entity.Contact contactDb = opportunity.getContact();
        if (opportunity != null && agentDetails != null) {
            if (!mailSentAlready( crmOpportunityId, contactDb.getStage(), recipient, agentDetails )) {
                final AgentRating agentRating = saveInDB(contactDb, contact, agentDetails );
                final EmailNotification notification = feedbackEmailNotificationBuilder
                        .createEmailNotification( recipient, agentEmail, emailTemplate, agentRating.getId() );
                LOGGER.info( "Sending feedback email for Opp: " + contactDb.getCrmId() + ", Stage: "
                        + contactDb.getStage() + " to " + recipient );
                mailService.send( notification );
            } else {
                LOGGER.info( "Mail was sent to " + recipient + " already" );
            }
        } else {
            LOGGER.warn( "Opportunity/AgentDetails missing in database" );
        }
    }

    /**
     * Populate agent email.
     *
     * @param agentId
     *            the agent id
     * @return the string
     */
    private String getAgentEmail( final String agentId ) {
        final Search searchByAgentId = searchService.searchByAgentId( agentId );
        final String agentEmail = searchByAgentId.getAgentEmail();
        LOGGER.info( "Agent's email is: " + agentEmail );
        return agentEmail;
    }

    /**
     * Mail sent already.
     *
     * @param crmId
     *            the crm id
     * @param stage
     *            the stage
     * @param clientEmail
     *            the client email
     * @param agentDetails
     *            the agent details
     * @return true, if successful
     */
    private boolean mailSentAlready( final String crmId, final String stage, final String clientEmail,
            final AgentDetails agentDetails ) {
        final AgentRating agentRating = agentRatingService.findByCrmIdAndStageAndClientEmailAndAgentDetails( crmId,
                stage, clientEmail, agentDetails );
        return agentRating != null;
    }

    /**
     * Save in DB.
     *
     * @param opportunity
     *            the opportunity
     * @param contact
     *            the contact
     * @param agentDetails
     *            the agent details
     * @return the agent rating
     */
    private AgentRating saveInDB(final com.owners.gravitas.domain.entity.Contact contactDb, final Contact contact,
            final AgentDetails agentDetails ) {
        LOGGER.info( "saving feedback details in DB for agent " + agentDetails.getUser().getEmail() + " for opp: "
                + contactDb.getCrmId() + " for stage " + contactDb.getStage() );
        final AgentRating agentRating = new AgentRating();
        agentRating.setClientEmail( contact.getEmails().get( 0 ) );
        agentRating.setClientFirstName( contact.getFirstName() );
        agentRating.setClientLastName( contact.getLastName() );
        agentRating.setAgentDetails( agentDetails );
        agentRating.setCrmId( contactDb.getCrmId() );
        agentRating.setCrmObjectType( OPPORTUNITY.getName() );
        agentRating.setStage( contactDb.getStage() );
        return agentRatingService.save( agentRating );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentRatingBusinessService#saveFeedback(com.
     * owners.gravitas.dto.request.FeedbackRequest)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void saveFeedback( final FeedbackRequest feedbackRequest ) {
        final String ratingId = feedbackRequest.getRatingId();
        final AgentRating agentRating = agentRatingService.findOne( ratingId );
        if (agentRating != null) {
            feedbackValidator.validateMaxAttempts( agentRating.getFeedbackReceivedCount() );
            feedbackValidator.checkEmailExpiry( agentRating );
            LOGGER.info( "Saving feedback for client: " + agentRating.getClientEmail() + ", agent: "
                    + agentRating.getAgentDetails().getUser().getEmail() );
            agentRating.setRating( feedbackRequest.getRating() );
            agentRating.setFeedbackReceivedCount( agentRating.getFeedbackReceivedCount() + 1 );
            agentRating.setFeedback( feedbackRequest.getFeedback() );
            agentRating.setComments( feedbackRequest.getComments() );
            agentRatingService.save( agentRating );
        } else {
            throw new ApplicationException( INVALID_RATING_ID_RECEIVED.getErrorDetail() + SPACE + ratingId,
                    INVALID_RATING_ID_RECEIVED );
        }
    }
}
