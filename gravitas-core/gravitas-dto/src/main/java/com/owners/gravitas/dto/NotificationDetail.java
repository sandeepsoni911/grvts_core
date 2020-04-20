package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class NotificationDetail.
 * 
 * @author javeedsy
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class NotificationDetail extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3767447741473487818L;
    /** The event text. */
    private String from;
    /** The email notification subject text. */
    private String subject;
    /** The email notification sent on. */
    private Long sentOn;
    /** The email notification trigger text. */
    private String trigger;

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject( String subject ) {
        this.subject = subject;
    }

    public Long getSentOn() {
        return sentOn;
    }

    public void setSentOn( Long sentOn ) {
        this.sentOn = sentOn;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger( String trigger ) {
        this.trigger = trigger;
    }

}
