package com.owners.gravitas.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.enums.SubscriptionType;

/**
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserNotificationConfigDetails implements Serializable {

    /** The serial version UID **/
    private static final long serialVersionUID = 4931455239521266234L;

    private String userId;
    
    private String emailId;

    private String notifySavedSearchId;

    private String subscribeType;

    private NotificationType notificationType;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId( String userId ) {
        this.userId = userId;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId( String emailId ) {
        this.emailId = emailId;
    }

    /**
     * @return the notifySavedSearchId
     */
    public String getNotifySavedSearchId() {
        return notifySavedSearchId;
    }

    /**
     * @param notifySavedSearchId
     *            the notifySavedSearchId to set
     */
    public void setNotifySavedSearchId( String notifySavedSearchId ) {
        this.notifySavedSearchId = notifySavedSearchId;
    }

    /**
     * @return the subscribeType
     */
    public String getSubscribeType() {
        return subscribeType;
    }

    /**
     * @param subscribeType
     *            the subscribeType to set
     */
    public void setSubscribeType( String subscribeType ) {
        this.subscribeType = subscribeType;
    }

    /**
     * @return the notificationType
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * @param notificationType
     *            the notificationType to set
     */
    public void setNotificationType( NotificationType notificationType ) {
        this.notificationType = notificationType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append( "UserNotificationConfigDetails [userId=" ).append( userId ).append( ", emailId=" ).append( emailId )
                .append( ", notifySavedSearchId=" ).append( notifySavedSearchId ).append( ", subscribeType=" )
                .append( subscribeType ).append( ", notificationType=" + notificationType ).append( "]" );

        return sb.toString();
    }
}
