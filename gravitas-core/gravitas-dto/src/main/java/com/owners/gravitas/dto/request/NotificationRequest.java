package com.owners.gravitas.dto.request;

import com.hubzu.notification.dto.client.push.AppType;
import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Class NotificationRequest.
 *
 * @author vishwanathm
 */
public class NotificationRequest extends BaseDTO {

    /** serialVersionUID. */
    private static final long serialVersionUID = -3735789928425265187L;

    /** The event type. */
    private PushNotificationType eventType;

    /** The device token. */
    private String deviceToken;

    /** The device type. */
    private AppType deviceType;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The opportunity id. */
    private String opportunityId;

    /** The task id. */
    private String taskId;

    /** The title. */
    private String title;

    /** The trigger dtm. */
    private Long triggerDtm;

    /** The opportunity count. */
    private int opportunityCount;

    /** The request type. */
    private String requestType;

    /**
     * Instantiates a new notification request.
     */
    public NotificationRequest() {
        // do nothing
    }

    /**
     * Instantiates a new notification request.
     *
     * @param opportunityCount
     *            the opportunity count
     */
    public NotificationRequest( final int opportunityCount ) {
        this.opportunityCount = opportunityCount;
    }

    /**
     * Instantiates a new notification request.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @param eventType
     *            the event type
     * @param taskId
     *            the task id
     * @param opportunityId
     *            the opportunity id
     */
    public NotificationRequest( final String firstName, final String lastName, final PushNotificationType eventType,
            final String taskId, final String opportunityId ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eventType = eventType;
        this.taskId = taskId;
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the message type name.
     *
     * @return the eventType
     */
    public PushNotificationType getEventType() {
        return eventType;
    }

    /**
     * Sets the message type name.
     *
     * @param messageTypeName
     *            the new event type
     */
    public void setEventType( final PushNotificationType eventType ) {
        this.eventType = eventType;
    }

    /**
     * Gets the device token.
     *
     * @return the deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * Sets the device token.
     *
     * @param deviceToken
     *            the deviceToken to set
     */
    public void setDeviceToken( final String deviceToken ) {
        this.deviceToken = deviceToken;
    }

    /**
     * Gets the device type.
     *
     * @return the device type
     */
    public AppType getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the device type.
     *
     * @param deviceType
     *            the new device type
     */
    public void setDeviceType( final AppType deviceType ) {
        this.deviceType = deviceType;
    }

    /**
     * Gets the first name.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the lastName to set
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets the opportunity Id.
     *
     * @return the opportunity Id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity Id.
     *
     * @param opportunityId
     *            the new opportunity Id
     */
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the task id.
     *
     * @return the task id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the task id.
     *
     * @param taskId
     *            the new task id
     */
    public void setTaskId( final String taskId ) {
        this.taskId = taskId;
    }

    /**
     * Gets the opportunity count.
     *
     * @return the opportunity count
     */
    public int getOpportunityCount() {
        return opportunityCount;
    }

    /**
     * Sets the opportunity count.
     *
     * @param opportunityCount
     *            the new opportunity count
     */
    public void setOpportunityCount( final int opportunityCount ) {
        this.opportunityCount = opportunityCount;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the title to set
     */
    public void setTitle( final String title ) {
        this.title = title;
    }

    /**
     * Gets the trigger dtm.
     *
     * @return the triggerDtm
     */
    public Long getTriggerDtm() {
        return triggerDtm;
    }

    /**
     * Sets the trigger dtm.
     *
     * @param triggerDtm
     *            the triggerDtm to set
     */
    public void setTriggerDtm( final Long triggerDtm ) {
        this.triggerDtm = triggerDtm;
    }

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the request type.
     *
     * @param requestType
     *            the new request type
     */
    public void setRequestType( final String requestType ) {
        this.requestType = requestType;
    }

}
