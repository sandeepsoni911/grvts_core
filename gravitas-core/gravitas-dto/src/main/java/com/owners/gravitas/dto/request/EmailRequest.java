package com.owners.gravitas.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.owners.gravitas.validators.EmailList;
import com.owners.gravitas.validators.ItemSize;
import com.owners.gravitas.validators.UrlList;

/**
 * The Class EmailRequest.
 *
 * @author vishwanathm
 */
public class EmailRequest {

    /** The to. */
    @NotNull( message = "error.agent.email.to.required" )
    @Valid
    @Size( min = 1, max = 100, message = "error.agent.email.to.size" )
    @ItemSize( min = 1, max = 80, message = "error.agent.email.toEmail.size" )
    @EmailList( message = "error.agent.email.to.format" )
    private List< String > to;

    /** The to. */
    @ItemSize( min = 1, max = 80, message = "error.agent.email.ccEmail.size" )
    @EmailList( message = "error.agent.email.cc.format" )
    @Size( max = 100, message = "error.agent.email.cc.size" )
    private List< String > cc;

    /** The to. */
    @ItemSize( min = 1, max = 80, message = "error.agent.email.bccEmail.size" )
    @EmailList( message = "error.agent.email.bcc.format" )
    @Size( max = 100, message = "error.agent.email.bcc.size" )
    private List< String > bcc;

    /** The subject. */
    @NotBlank( message = "error.agent.email.subject.required" )
    @Size( min = 1, max = 998, message = "error.agent.email.subject.size" )
    private String subject;

    /** The body text. */
    @NotBlank( message = "error.agent.email.bodyText.required" )
    @Size( min = 1, max = 7500000, message = "error.agent.email.bodyText.size" )
    private String bodyText;

    /** The attachment urls. */
    @UrlList( message = "error.agent.email.attachmentUrls.format" )
    @Size( min = 0, max = 10, message = "error.agent.email.attachmentUrls.size" )
    @ItemSize( min = 7, max = 2000, message = "error.agent.email.attachmentUrls.item.size" )
    private List< String > attachmentUrls;

    /**
     * Gets the to.
     *
     * @return the to
     */
    public List< String > getTo() {
        return to;
    }

    /**
     * Sets the to.
     *
     * @param to
     *            the new to
     */
    public void setTo( final List< String > to ) {
        this.to = to;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject
     *            the new subject
     */
    public void setSubject( final String subject ) {
        this.subject = subject;
    }

    /**
     * Gets the body text.
     *
     * @return the body text
     */
    public String getBodyText() {
        return bodyText;
    }

    /**
     * Sets the body text.
     *
     * @param bodyText
     *            the new body text
     */
    public void setBodyText( final String bodyText ) {
        this.bodyText = bodyText;
    }

    /**
     * Gets the attachment urls.
     *
     * @return the attachment urls
     */
    public List< String > getAttachmentUrls() {
        return attachmentUrls;
    }

    /**
     * Sets the attachment urls.
     *
     * @param attachmentUrls
     *            the new attachment urls
     */
    public void setAttachmentUrls( final List< String > attachmentUrls ) {
        this.attachmentUrls = attachmentUrls;
    }

    /**
     * Gets the cc.
     *
     * @return the cc
     */
    public List< String > getCc() {
        return cc;
    }

    /**
     * Sets the cc.
     *
     * @param cc
     *            the new cc
     */
    public void setCc( final List< String > cc ) {
        this.cc = cc;
    }

    /**
     * Gets the bcc.
     *
     * @return the bcc
     */
    public List< String > getBcc() {
        return bcc;
    }

    /**
     * Sets the bcc.
     *
     * @param bcc
     *            the new bcc
     */
    public void setBcc( final List< String > bcc ) {
        this.bcc = bcc;
    }
}
