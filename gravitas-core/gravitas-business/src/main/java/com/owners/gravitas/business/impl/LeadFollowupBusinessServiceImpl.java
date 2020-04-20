package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.LOST_STATUS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.flowable.engine.RuntimeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.business.builder.LeadFollowupNotificationBuilder;
import com.owners.gravitas.config.TimerConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.MarketingEmailLog;
import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.service.LeadFollowupLogService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.NoteService;
import com.owners.gravitas.service.StateTimeZoneService;

/**
 * The Class LeadFollowupBusinessServiceImpl handles,
 * 1) Triggering lead follow up email process.
 * 2) Send's email of different stages on bases of the present buyer lead status
 * verifying against the CRM.
 *
 * @author vishwanathm
 */
@Service
public class LeadFollowupBusinessServiceImpl implements LeadFollowupBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadFollowupBusinessServiceImpl.class );

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The marketing email notification builder. */
    @Autowired
    private LeadFollowupNotificationBuilder leadFollowupNotificationBuilder;

    /** The timerConfig. */
    @Autowired
    private TimerConfig timerConfig;

    /** The lead source string. */
    @Value( "${lead.marketing.email.leadSource}" )
    private String leadSourceString;

    /** The lead source arr. */
    private List< String > leadSources;

    /** The time zone service. */
    @Autowired
    private StateTimeZoneService stateTimeZoneService;

    /** The marketing email log service. */
    @Autowired
    private LeadFollowupLogService leadFollowupLogService;

    /** The Constant FINAL_OUTREACH_EMAIL_DURATION. */
    private static final String FINAL_OUTREACH_EMAIL_DURATION = "finalOutreachEmailDuration";

    /** The Constant MAKE_YOUR_AGENT_EMAIL_DURATION. */
    private static final String MAKE_YOUR_AGENT_EMAIL_DURATION = "makeYourAgentEmailDuration";

    /** The Constant SCHEDULE_TOUR_EMAIL_DURATION. */
    private static final String SCHEDULE_TOUR_EMAIL_DURATION = "scheduleTourEmailDuration";

    /** The Constant REBATE_EMAIL_DURATION. */
    private static final String REBATE_EMAIL_DURATION = "rebateEmailDuration";

    /** The Constant START_YOUR_SEARCH_START_DATE_TIME. */
    private static final String START_YOUR_SEARCH_START_DATE_TIME = "startYourSearchStartDateTime";

    /** The Constant EMAIL_TEMPLATE_KEY. */
    private static final String EMAIL_TEMPLATE_KEY = "EMAIL_TEMPLATE";

    /** The Constant INTRODUCTION_TEMPLATE. */
    private static final String INTRODUCTION_TEMPLATE = "MARKETING_EMAIL_INTRODUCTION";

    /** The start your search template. */
    private static final String START_YOUR_SEARCH_TEMPLATE = "MARKETING_EMAIL_START_YOUR_SEARCH";

    /** The rebate template. */
    private static final String REBATE_TEMPLATE = "MARKETING_EMAIL_REBATE";

    /** The schedule tour template. */
    private static final String SCHEDULE_TOUR_TEMPLATE = "MARKETING_EMAIL_SCHEDULE_TOUR";

    /** The make your agent template. */
    private static final String MAKE_YOUR_AGENT_TEMPLATE = "MARKETING_EMAIL_MAKE_YOUR_AGENT";

    /** The final outreach template. */
    private static final String FINAL_OUTREACH_TEMPLATE = "MARKETING_EMAIL_FINAL_OUTREACH";

    /** The note service. */
    @Autowired
    private NoteService noteService;

    /** The property replyToEmailId */
    @Value( "${lead.marketing.email.replyTo}" )
    private String replyToEmailId;

    /**
     * Start marketing email process, only in case if the buyer lead status is
     * 'Outbound Attempt', belong to defined lead sources and the lead source
     * should not be 'Unbounce landing page'.
     *
     * @param String
     *            dataNode
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void startLeadFollowupEmailProcess( final LeadSource leadSource ) {
        if (isEligibleForMarketingEmails( leadSource )) {
            LOGGER.info( "Lead " + leadSource.getId() + ", having email id as: " + leadSource.getEmail()
                    + " is eligible for lead follow up emails" );
            final MarketingEmailLog log = leadFollowupLogService.getMarketingEmailLogBuyLeadId( leadSource.getId() );
            if (log == null) {
                LOGGER.info( "Lead follow up process started for lead " + leadSource.getId() );
                final Map< String, Object > params = initParams( leadSource );
                saveMarketingEmailLog( leadSource.getId() );
                runtimeService.startProcessInstanceByKey( "marketingEmailProcess", params );
            }
        }
    }

    /**
     * Send marketing email to buyer email with respective lead owner details by
     * retrieving configured email template from notification engine.
     *
     * @param id
     *            the id
     * @param ownerId
     *            the owner id
     */
    @Override
    public void sendLeadFollowupEmails( final String executionId, final LeadSource leadSource ) {
    	LOGGER.info("ExecutionId:" + executionId + "::LEAD ID:" + leadSource.getId());
        final EmailNotification emailNotification = getEmailNotification( executionId, leadSource );
        final NotificationResponse response = mailService.send( emailNotification );
        if ("success".equalsIgnoreCase( response.getStatus() )) {
        	LOGGER.info("Mail sent successfully for " + leadSource.getEmail());
            final CrmNote crmNote = new CrmNote( leadSource.getId(),
                    Constants.NOTE_EMAIL_SENT_SUBJECT + emailNotification.getMessageTypeName(), null );
            noteService.saveNote( crmNote );
        }
    }

    /**
     * Clean marketing email log.
     *
     * @param id
     *            the id
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void cleanLeadFollowupLog( final String leadId ) {
        leadFollowupLogService
                .deleteMarketingEmailLog( leadFollowupLogService.getMarketingEmailLogBuyLeadId( leadId ) );
    }

    /**
     * Inits the lead sources.
     */
    @PostConstruct
    private void initLeadSources() {
        leadSources = Arrays.asList( leadSourceString.split( COMMA ) );
    }

    /**
     * Gets the email notification.
     *
     * @param executionId
     *            the execution id
     * @param leadSource
     *            the lead source
     * @return the email notification
     */
    private EmailNotification getEmailNotification( final String executionId, final LeadSource leadSource ) {
        final EmailNotification emailNotification = leadFollowupNotificationBuilder.convertTo( leadSource );
        final String tempalteName = getEmailTemplateName( executionId );
        emailNotification.setMessageTypeName( tempalteName );
        emailNotification.getEmail().setReplyToEmail( replyToEmailId );
        return emailNotification;
    }

    /**
     * Inits the params.
     *
     * @param dataNode
     *            the data node
     * @param leadId
     *            the lead id
     * @return the map
     */
    private Map< String, Object > initParams( final LeadSource leadSource ) {
        final Map< String, Object > params = addTimers( leadSource.getState(), new HashMap<>() );
        params.put( LEAD, leadSource );
        return params;
    }

    /**
     * Save marketing email log.
     *
     * @param leadId
     *            the lead id
     */
    private void saveMarketingEmailLog( final String leadId ) {
        final MarketingEmailLog emailLog = new MarketingEmailLog();
        emailLog.setLeadId( leadId );
        emailLog.setCreatedDate( new DateTime() );
        leadFollowupLogService.saveMarketingEmailLog( emailLog );
    }

    /**
     * Gets the email template name for current execution and set the next
     * template for execution.
     *
     * @param executionId
     *            the execution id
     * @return the email template name
     */
    private String getEmailTemplateName( final String executionId ) {
        final Object tempateNameObj = runtimeService.getVariable( executionId, EMAIL_TEMPLATE_KEY );
        final String templateName = ( tempateNameObj != null ) ? tempateNameObj.toString() : StringUtils.EMPTY;
        boolean isLost = Boolean.FALSE;
        switch ( templateName ) {
            case INTRODUCTION_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, START_YOUR_SEARCH_TEMPLATE );
                break;
            case START_YOUR_SEARCH_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, REBATE_TEMPLATE );
                break;
            case REBATE_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, SCHEDULE_TOUR_TEMPLATE );
                break;
            case SCHEDULE_TOUR_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, MAKE_YOUR_AGENT_TEMPLATE );
                break;
            case MAKE_YOUR_AGENT_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, FINAL_OUTREACH_TEMPLATE );
                break;
            case FINAL_OUTREACH_TEMPLATE:
                runtimeService.setVariable( executionId, EMAIL_TEMPLATE_KEY, StringUtils.EMPTY );
                isLost = Boolean.TRUE;
                LOGGER.info( "Lead followup process completed." );
                break;
            default:
                break;
        }
        runtimeService.setVariable( executionId, LOST_STATUS, isLost );
        return templateName;
    }

    /**
     * Checks if is valid lead source against the configured lead sources.
     *
     * @param leadSource
     *            the lead source
     * @return true, if is lead source exists
     */
    private boolean isValidLeadSource( final String leadSource ) {
        return leadSources.contains( leadSource.toLowerCase() );
    }

    /**
     * Adds/configures the timers to current process started.
     *
     * @param stateCode
     *            the state code
     * @param timers
     *            the timers
     * @return the map
     */
    private Map< String, Object > addTimers( final String stateCode, final Map< String, Object > timers ) {
        timers.put( START_YOUR_SEARCH_START_DATE_TIME, getStartYourSearchDateTimeForState( stateCode ) );
        timers.put( REBATE_EMAIL_DURATION, timerConfig.getRebateDuration() );
        timers.put( SCHEDULE_TOUR_EMAIL_DURATION, timerConfig.getScheduleTourDuration() );
        timers.put( MAKE_YOUR_AGENT_EMAIL_DURATION, timerConfig.getMarketingYourAgentDuration() );
        timers.put( FINAL_OUTREACH_EMAIL_DURATION, timerConfig.getFinalOutreachDuration() );
        return timers;
    }

    /**
     * Gets the start your search date time for state, by computing the time
     * zone for given state code.
     *
     * @param stateCode
     *            the state code
     * @return the next date time
     */
    private String getStartYourSearchDateTimeForState( final String stateCode ) {
        final StateTimeZone stateTimezone = stateTimeZoneService.getStateTimeZone( stateCode );
        final Integer offsetHour = ( stateTimezone != null && stateTimezone.getOffSetHour() != null )
                ? stateTimezone.getOffSetHour() : 0;
        final DateTimeZone timeZone = DateTimeZone.forOffsetHours( offsetHour );
        final DateTime now = DateTime.now( timeZone );
        final DateTime startSearchDateTime = now
                .withTime( timerConfig.getStartYourSearchHour(), timerConfig.getStartYourSearchMinute(), 0, 0 )
                .plusDays( timerConfig.getStartYourSearchDuration() ).toDateTime();
        return startSearchDateTime.toString();
    }

    /**
     * Check whether lead has opted out of marketing emails
     *
     * @param leadSource
     * @return
     */
    private boolean hasOptedForDnd( final LeadSource leadSource ) {
        if (leadSource.isDoNotEmail()) {
            return Boolean.TRUE;
        }

        NotificationPreferenceResponse notificationPreferenceResponse = mailService
                .getNotificationPreferenceForUser( leadSource.getEmail() );
        final List< Preference > preferences = notificationPreferenceResponse.getPreferences();
        if (CollectionUtils.isNotEmpty( preferences )) {
            return preferences.stream().anyMatch(
                    p -> NotificationType.MARKETING.name().equalsIgnoreCase( p.getType() ) && !p.getValue() );
        }

        return Boolean.FALSE;
    }

    /**
     * Determine whether this lead (user) is eligible for receiving marketing
     * mails
     *
     * @param leadSource
     * @return
     */
    private boolean isEligibleForMarketingEmails( final LeadSource leadSource ) {
        return isValidLeadSource( leadSource.getSource() ) && !hasOptedForDnd( leadSource );
    }

}
