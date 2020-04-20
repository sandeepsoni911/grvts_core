package com.owners.gravitas.handler;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.enums.PushNotificationType.UPDATE_BADGE_COUNTER;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.StageLogService;

/**
 * Abstract class to handle Opportunity Changes.
 *
 * @author Khanujal, ankusht
 *
 */
public abstract class OpportunityChangeHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityChangeHandler.class );

    /** The feedback email jmx config. */
    @Autowired
    protected FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The mail service. */
    @Autowired
    protected MailService mailService;

    /** The opportunity service. */
    @Autowired
    protected OpportunityService opportunityService;

    /** The agent business service. */
    @Autowired
    protected AgentBusinessService agentBusinessService;

    /** The agent business service. */
    @Autowired
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The agent rating business service. */
    @Autowired
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The account service. */
    @Autowired
    private AccountService accountService;

    /** The stage log service. */
    @Autowired
    protected StageLogService stageLogService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The badge counter jmx config. */
    @Autowired
    protected BadgeCounterJmxConfig badgeCounterJmxConfig;

    /**
     * Handle change.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param contact
     *            the contact
     * @return the post response
     */
    public abstract PostResponse handleChange( final String agentId, final String opportunityId,
            final Contact contact );

    /**
     * Handle change.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param contact
     *            the contact
     * @param stage
     *            the stage
     * @return the post response
     */
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact,
            final String stage ) {
        stageLogService.saveOpportunityStagelog( opportunityId, stage );
        return handleChange( agentId, opportunityId, contact );

    }

    /**
     * Save.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param contact
     *            the contact
     * @param type
     *            the type
     * @param title
     *            the title
     * @return the post response
     */
    protected PostResponse saveTask( final String agentId, final String opportunityId, final Contact contact,
            final TaskType type, final String title ) {
        PostResponse response = null;
        if (null != contact) {
            final String name = getName( contact.getFirstName(), contact.getLastName() );
            response = agentTaskBusinessService.saveTaskifNotExists( agentId, opportunityId, type, title, name );
        }
        return response;
    }

    /**
     * Close previous stage task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     */
    protected void closePreviousStageTask( final String agentId, final String opportunityId, final TaskType type ) {
        agentTaskBusinessService.closeTaskByType( agentId, opportunityId, type );
    }

    /**
     * Gets the name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the name
     */
    protected String getName( final String firstName, final String lastName ) {
        return StringUtils.isNotBlank( firstName ) ? firstName + BLANK_SPACE + lastName : lastName;
    }

    /**
     * Builds the task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param contact
     *            the contact
     * @return the task
     */
    public abstract Task buildTask( final String agentId, final String opportunityId, final Contact contact );

    /**
     * Builds the task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param contact
     *            the contact
     * @param type
     *            the type
     * @param title
     *            the title
     * @return the task
     */
    protected Task buildTask( final String agentId, final String opportunityId, final Contact contact,
            final TaskType type, final String title ) {
        final String titleTxt = String.format( title, getName( contact.getFirstName(), contact.getLastName() ) );
        final Long now = new DateTime().getMillis();
        return new Task( titleTxt, opportunityId, type.getType(), agentId, now );
    }

    /**
     * Send feedback email.
     *
     * @param agentId
     *            the agent id
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param contact
     *            the contact
     */
    public abstract void sendFeedbackEmail( final String agentId, final String crmOpportunityId,
            final Contact contact );

    /**
     * Send email.
     *
     * @param emailTemplate
     *            the email template
     * @param agentId
     *            the agent id
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param contact
     *            the contact
     */
    protected void sendEmail( final String emailTemplate, final String agentId, final String crmOpportunityId,
            final Contact contact ) {
        agentRatingBusinessService.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
    }

    /**
     * Gets the opportunity source.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity source
     */
    protected OpportunitySource getOpportunitySource( final String crmId ) {
        OpportunitySource opportunitySource = null;
        try {
            opportunitySource = opportunityBusinessService.getOpportunity( crmId );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.error( "Opportunity details not found on CRM for: " + crmId );
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
        return opportunitySource;
    }

    /**
     * Gets the agent source.
     *
     * @param agentId
     *            the agent id
     * @return the agent source
     */
    protected AgentSource getAgentSource( final String agentId ) {
        final Search searchByAgentId = searchService.searchByAgentId( agentId );
        AgentSource agentSource = null;
        try {
            agentSource = agentBusinessService.getCRMAgentByEmail( searchByAgentId.getAgentEmail() );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.error( "Agent details not found on CRM for: " + searchByAgentId.getAgentEmail() );
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
        return agentSource;
    }

    /**
     * Gets the account name.
     *
     * @param crmId
     *            the crm id
     * @return the account name
     */
    protected String getAccountName( final String crmId ) {
        final String accountId = opportunityService.getTitleClosingCompanyByOpportunityId( crmId );
        String accountName = null;
        try {
            accountName = accountService.findAccountNameById( accountId );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.debug( "No PTS account linked to the opportunity: " + crmId, e );
        }
        return accountName;
    }

    /**
     * handle the badge counter change in case opportunity stage changes.
     *
     * @param agentId
     *            the agent id
     */
    public void handleBadgeCounterChange( final String agentId ) {
        if (badgeCounterJmxConfig.isBadgeCountEnabled()) {
            final NotificationRequest notificationRequest = new NotificationRequest(
                    agentOpportunityService.getAgentNewOpportunitiesCount( agentId ) );
            notificationRequest.setEventType( UPDATE_BADGE_COUNTER );
            agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
        }
    }
}
