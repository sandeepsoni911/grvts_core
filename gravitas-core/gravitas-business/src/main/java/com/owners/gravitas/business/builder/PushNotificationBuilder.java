package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.APPOINTMENT_TEXT;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.BUYER_NAME;
import static com.owners.gravitas.constants.Constants.FIRST_NAME;
import static com.owners.gravitas.constants.Constants.BUYER_OFFER_TEXT;
import static com.owners.gravitas.constants.Constants.DYNAMIC_TEXT;
import static com.owners.gravitas.constants.Constants.INQUIRY_TEXT;
import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.Constants.NOTIFICATION_TYPE;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;
import static com.owners.gravitas.constants.Constants.TASK_ID;
import static com.owners.gravitas.constants.Constants.TASK_TITLE;
import static com.owners.gravitas.enums.ErrorCode.UNSUPPORTED_NOTIFICATION_TYPE;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.push.AppType;
import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class PushNotificationBuilder.
 *
 * @author harshads
 */
@Component
public class PushNotificationBuilder extends AbstractBuilder< NotificationRequest, PushNotification > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PushNotificationBuilder.class );

    /** The Constant NEW_REQUEST_NOTIFICATION_TYPE. */
    private static final String NEW_REQUEST_NOTIFICATION_TYPE = "NEW_REQUEST";

    /** The Constant NEW_REQUEST_NOTIFICATION_TYPE. */
    private static final String NEW_TASK_NOTIFICATION_TYPE = "NEW_TASK";

    /** The Constant NEW_OPPORTUNITY_NOTIFICATION_TYPE. */
    private static final String NEW_OPPORTUNITY_NOTIFICATION_TYPE = "NEW_OPPORTUNITY";
    
    /** The Constant NEW_OPPORTUNITY_NOTIFICATION_TYPE. */
    private static final String NOTIFICATION_OCL_REMINDER = "NOTIFICATION_OCL_REMINDER";

    /** The Constant OPPORTUNITY_COUNT. */
    private static final String OPPORTUNITY_COUNT = "OPPORTUNITY_COUNT";

    /** The Constant OPPORTUNITY_LABEL. */
    private static final String OPPORTUNITY_LABEL = "OPPORTUNITY_LABEL";

    /** The Constant OPPORTUNITY_VERB_LABEL. */
    private static final String OPPORTUNITY_VERB_LABEL = "OPPORTUNITY_VERB_LABEL";

    /** The Constant OPPORTUNITY_PRONOUN_LABEL. */
    private static final String OPPORTUNITY_PRONOUN_LABEL = "OPPORTUNITY_PRONOUN_LABEL";

    /** The Constant SCRIPTED_FLOW_NEW_OPPORTUNITY. */
    private static final String SCRIPTED_FLOW_NEW_OPPORTUNITY = "SCRIPTED_FLOW_NEW_OPPORTUNITY";

    /** The Constant SCRIPTED_FLOW_CALL_REMINDER. */
    private static final String SCRIPTED_FLOW_CALL_REMINDER = "SCRIPTED_FLOW_CALL_REMINDER";

    /** The Constant SCRIPTED_FLOW_INCOMPLETE. */
    private static final String SCRIPTED_FLOW_INCOMPLETE = "SCRIPTED_FLOW_INCOMPLETE";

    /** The Constant ON_DUTY_AGENT. */
    private static final String ON_DUTY_AGENT = "ON_DUTY_AGENT";

    /** The Constant CLIENT. */
    private static final String CLIENT = "Client";

    /** The Constant CLIENTS. */
    private static final String CLIENTS = "Clients";

    /** The buyer message type name. */
    @Value( "${buyer.request.message.type.name}" )
    private String buyerMessageTypeName;

    /** The opportunity message type name. */
    @Value( "${opportunity.request.message.type.name}" )
    private String opportunityMessageTypeName;

    /** The task message type name. */
    @Value( "${task.request.message.type.name}" )
    private String taskMessageTypeName;

    /** The new opp reminder message type name. */
    @Value( "${reminder.new.opportunity.request.message.type.name}" )
    private String newOppReminderMessageTypeName;

    /** The claimed opp reminder message type name. */
    @Value( "${reminder.claimed.opportunity.request.message.type.name}" )
    private String claimedOppReminderMessageTypeName;

    /** The schedule task alert message type name. */
    @Value( "${scheduled.task.alert.message.type.name}" )
    private String scheduleTaskAlertMessageTypeName;

    /** The scheduled task reminder message type name. */
    @Value( "${scheduled.task.reminder.message.type.name}" )
    private String scheduledTaskReminderMessageTypeName;
    
    /** The scheduled task reminder message type name. */
    @Value( "${ocl.reminder.message.type.name}" )
    private String oclReminderMessageTypeName;

    /** The scripted flow opp assign message type name. */
    @Value( "${scripted.flow.assign.opportunity.message.type.name}" )
    private String scriptedFlowOppAssignMessageTypeName;

    /** The scripted flow call action message type name. */
    @Value( "${scripted.flow.call.opportunity.message.type.name}" )
    private String scriptedFlowCallActionMessageTypeName;

    /** The app name code. */
    @Value( "${notification.app.name.code}" )
    private String appNameCode;

    /** The app name code. */
    @Value( "${push.notification.priority}" )
    private Integer notificationPriority;

    /** The Constant BADGE. */
    private static final String BADGE = "badge";

    /** The update badge counter message type name. */
    @Value( "${update.badge.counter.message.type.name}" )
    private String updatebadgeCounterMessageTypeName;

    /** The scripted flow reminder message type name. */
    @Value( "${scripted.flow.reminder.incomplete.message.type.name}" )
    private String scriptedFlowReminderMessageTypeName;

    @Value( "${onduty.agent.message.type.name}" )
    private String onDutyAgentMessageTypeName;

    /** The badge counter jmx config. */
    @Autowired
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /** The task message type name. */
    @Value( "${push.notification.generic.sound.file.name}" )
    private String genericSoundFileName;

    /** The task message type name. */
    @Value( "${push.notification.new.client.scripted.call.sound.file.name}" )
    private String scriptedCallFileName;

    /** The Constant SOUND. */
    private static final String SOUND = "sound";
    
    
    /** The Constant SCRIPTED_FLOW_INCOMPLETE. */
    private static final String NEW_PENDING_OPPORTUNITY = "NEW_PENDING_OPPORTUNITY";
    
    @Value("${schedule.tour.confirmed.message.type.name}")
    private String newPendingOpprtunityTypeName;

    /**
     * converts {@link NotificationRequest} to {@link PushNotification}.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the push notification
     */
    @Override
    public PushNotification convertTo( final NotificationRequest source, final PushNotification destination ) {
        PushNotification notification = destination;
        if (source != null) {
            if (notification == null) {
                notification = new PushNotification();
            }
            String messageTypeName = null;
            String notificationType = null;
            final Map< String, String > paramMap = new HashMap<>();
            final Map< String, String > dataMap = new HashMap<>();
            Map< String, String > notificationMap = new HashMap<>();
            notificationMap.put( SOUND, genericSoundFileName );
            switch ( source.getEventType() ) {
                case NEW_REQUEST:
                    messageTypeName = buyerMessageTypeName;
                    notificationType = NEW_REQUEST_NOTIFICATION_TYPE;
                    paramMap.put( BUYER_NAME, getName( source.getFirstName(), source.getLastName() ) );
                    if (StringUtils.isNotBlank( source.getRequestType() )) {
                        if (source.getRequestType().equalsIgnoreCase( LeadRequestType.MAKE_OFFER.toString() )) {
                            paramMap.put( DYNAMIC_TEXT, BUYER_OFFER_TEXT );
                        }
                        if (source.getRequestType().equalsIgnoreCase( LeadRequestType.SCHEDULE_TOUR.toString() )) {
                            paramMap.put( DYNAMIC_TEXT, APPOINTMENT_TEXT );
                            if(source.getTaskId() != null) {
                            	LOGGER.info( "Adding Task id:{} to dataMap for :{}", source.getTaskId(),
                            			getName( source.getFirstName(), source.getLastName() ) );
                            	dataMap.put( TASK_ID, source.getTaskId() );
                            }
                        }
                        if (source.getRequestType()
                                .equalsIgnoreCase( LeadRequestType.REQUEST_INFORMATION.toString() )) {
                            paramMap.put( DYNAMIC_TEXT, INQUIRY_TEXT );
                        }
                    }
                    break;
                case NEW_TASK:
                    messageTypeName = taskMessageTypeName;
                    notificationType = NEW_TASK_NOTIFICATION_TYPE;
                    paramMap.put( BUYER_NAME, getName( source.getFirstName(), source.getLastName() ) );
                    dataMap.put( TASK_ID, source.getTaskId() );
                    LOGGER.debug( "For " + messageTypeName + " the " + TASK_ID + " is " + source.getTaskId() );
                    break;
                case NEW_OPPORTUNITY:
                    messageTypeName = opportunityMessageTypeName;
                    notificationType = NEW_OPPORTUNITY_NOTIFICATION_TYPE;
                    paramMap.put( OPPORTUNITY_COUNT, String.valueOf( source.getOpportunityCount() ) );
                    paramMap.put( OPPORTUNITY_LABEL, source.getOpportunityCount() == 1 ? CLIENT : CLIENTS );
                    paramMap.put( OPPORTUNITY_PRONOUN_LABEL,
                            source.getOpportunityCount() == 1 ? "this " + CLIENT : "them" );
                    if (badgeCounterJmxConfig.isBadgeCountEnabled()) {
                        if (source.getDeviceType() == AppType.ANDROID) {
                            dataMap.put( BADGE, String.valueOf( source.getOpportunityCount() ) );
                        } else if (source.getDeviceType() == AppType.IOS) {
                            notificationMap.put( BADGE, String.valueOf( source.getOpportunityCount() ) );
                        }
                    }
                    if (source.getOpportunityCount() == 1) {
                        dataMap.put( OPPORTUNITY_ID, source.getOpportunityId() );
                        LOGGER.debug( " For " + messageTypeName + " the " + OPPORTUNITY_ID + " is "
                                + source.getOpportunityId() );
                    }
                    break;
                case NEW_OPPORTUNITY_REMINDER:
                    messageTypeName = newOppReminderMessageTypeName;
                    notificationType = NEW_OPPORTUNITY_NOTIFICATION_TYPE;
                    paramMap.put( OPPORTUNITY_COUNT, String.valueOf( source.getOpportunityCount() ) );
                    paramMap.put( OPPORTUNITY_LABEL, source.getOpportunityCount() == 1 ? CLIENT : CLIENTS );
                    paramMap.put( OPPORTUNITY_VERB_LABEL, source.getOpportunityCount() == 1 ? "is" : "are" );
                    paramMap.put( OPPORTUNITY_PRONOUN_LABEL, source.getOpportunityCount() == 1 ? "it" : "they" );
                    break;

                case CLAIMED_OPPORTUNITY_REMINDER:
                    messageTypeName = claimedOppReminderMessageTypeName;
                    notificationType = NEW_TASK_NOTIFICATION_TYPE;
                    break;

                case SCHEDULED_TASK_APPOINTMENT_ALERT:
                    messageTypeName = scheduleTaskAlertMessageTypeName;
                    notificationType = NEW_TASK_NOTIFICATION_TYPE;
                    paramMap.put( BUYER_NAME, getName( source.getFirstName(), source.getLastName() ) );
                    paramMap.put( TASK_TITLE, source.getTitle() );
                    dataMap.put( TASK_ID, source.getTaskId() );
                    break;

                case SCHEDULED_TASK_APPOINTMENT_REMINDER:
                    messageTypeName = scheduledTaskReminderMessageTypeName;
                    notificationType = NEW_TASK_NOTIFICATION_TYPE;
                    paramMap.put( BUYER_NAME, getName( source.getFirstName(), source.getLastName() ) );
                    paramMap.put( TASK_TITLE, source.getTitle() );
                    dataMap.put( TASK_ID, source.getTaskId() );
                    break;
                    
                case NOTIFICATION_OCL_REMINDER:
                    messageTypeName = oclReminderMessageTypeName;
                    notificationType = NOTIFICATION_OCL_REMINDER;
                    paramMap.put( BUYER_NAME, getName( source.getFirstName(), source.getLastName() ) );
                    paramMap.put( FIRST_NAME, source.getFirstName() );
                    dataMap.put( OPPORTUNITY_ID, source.getOpportunityId() );
                    break;

                case UPDATE_BADGE_COUNTER:
                    messageTypeName = updatebadgeCounterMessageTypeName;
                    if (source.getDeviceType() == AppType.ANDROID) {
                        dataMap.put( BADGE, String.valueOf( source.getOpportunityCount() ) );
                        notificationMap = null;
                    } else if (source.getDeviceType() == AppType.IOS) {
                        notificationMap.put( BADGE, String.valueOf( source.getOpportunityCount() ) );
                    }
                    break;

                case SCRIPTED_FLOW_NEW_OPPORTUNITY:
                    messageTypeName = scriptedFlowOppAssignMessageTypeName;
                    notificationType = SCRIPTED_FLOW_NEW_OPPORTUNITY;
                    notificationMap.put( SOUND, scriptedCallFileName );
                    break;

                case SCRIPTED_FLOW_CALL_REMINDER:
                    messageTypeName = scriptedFlowCallActionMessageTypeName;
                    notificationType = SCRIPTED_FLOW_CALL_REMINDER;
                    break;

                case SCRIPTED_FLOW_INCOMPLETE:
                    messageTypeName = scriptedFlowReminderMessageTypeName;
                    notificationType = SCRIPTED_FLOW_INCOMPLETE;
                    break;

                case ON_DUTY_AGENT:
                    messageTypeName = onDutyAgentMessageTypeName;
                    notificationType = ON_DUTY_AGENT;
                    break;
                    
                case NEW_PENDING_OPPORTUNITY:
                    messageTypeName = newPendingOpprtunityTypeName;
                    dataMap.put( TASK_ID, source.getTaskId() );
                    notificationType = NEW_PENDING_OPPORTUNITY;
                    LOGGER.debug( "For message type: {} the {} is {} ", messageTypeName, TASK_ID, source.getTaskId() );
                    break;

                default:
                    LOGGER.info( "Unsupported Notification type" );
                    throw new ApplicationException( "Unsupported Notification type, push notification cant be sent",
                            UNSUPPORTED_NOTIFICATION_TYPE );
            }

            dataMap.put( NOTIFICATION_TYPE, notificationType );

            notification.setClientId( NOTIFICATION_CLIENT_ID );
            notification.setAppNameCode( appNameCode );
            notification.setMessageTypeName( messageTypeName );
            notification.setDeviceType( source.getDeviceType() );
            notification.setDeviceToken( source.getDeviceToken() );
            notification.setParameterMap( paramMap );
            notification.setDataMap( dataMap );
            notification.setNotificationMap( notificationMap );
            notification.setPriority( notificationPriority );
            notification.setTriggerOn( source.getTriggerDtm() );
        }
        return notification;
    }

    /**
     * Method not supported.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the notification request
     */
    @Override
    public NotificationRequest convertFrom( final PushNotification source, final NotificationRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
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
    private String getName( final String firstName, final String lastName ) {
        String name = lastName;
        if (StringUtils.isNotBlank( firstName )) {
            name = firstName + BLANK_SPACE + lastName;
        }
        return name;
    }

}
