package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author gururasm
 *
 */
@Entity( name = "GR_NOTIFICATION_LOG" )
public class NotificationLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The crm id. */
    @Column( name = "CRM_ID", nullable = false )
    private String crmId;

    /** The phone. */
    @Column( name = "PHONE", nullable = true )
    private String phone;

    /** The email. */
    @Column( name = "EMAIL", nullable = true )
    private String email;

    /** The status id. */
    @Column( name = "STATUS", nullable = false )
    private String status;

    /** The status code. */
    @Column( name = "STATUS_CODE", nullable = true )
    private String statusCode;

    /** The status message. */
    @Column( name = "STATUS_MESSAGE", nullable = true )
    private String statusMessage;

    /** The request id. */
    @Column( name = "REQUEST_ID", nullable = true )
    private String requestId;

    /** The type. */
    @Column( name = "TYPE", nullable = true )
    private String type;

    /** The message type name. */
    @Column( name = "MESSAGE_TYPE_NAME", nullable = true )
    private String messageTypeName;

    /** The client id. */
    @Column( name = "CLIENT_ID", nullable = true )
    private String clientId;

    /**
     * @return the crmId
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * @param crmId
     *            the crmId to set
     */
    public void setCrmId( String crmId ) {
        this.crmId = crmId;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone( String phone ) {
        this.phone = phone;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode( String statusCode ) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage
     *            the statusMessage to set
     */
    public void setStatusMessage( String statusMessage ) {
        this.statusMessage = statusMessage;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     *            the requestId to set
     */
    public void setRequestId( String requestId ) {
        this.requestId = requestId;
    }

    /**
     * @return the messageTypeName
     */
    public String getMessageTypeName() {
        return messageTypeName;
    }

    /**
     * @param messageTypeName
     *            the messageTypeName to set
     */
    public void setMessageTypeName( String messageTypeName ) {
        this.messageTypeName = messageTypeName;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId( String clientId ) {
        this.clientId = clientId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( String email ) {
        this.email = email;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( String type ) {
        this.type = type;
    }
}
