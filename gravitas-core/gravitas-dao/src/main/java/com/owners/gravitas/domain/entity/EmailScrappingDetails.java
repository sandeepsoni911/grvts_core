package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
/**
 * To store email scrapping details
 * like marathon response email metadata
 * @author sandeepsoni
 *
 */
@Entity( name = "gr_email_scrapping_details" )
public class EmailScrappingDetails extends AbstractAuditable {
	
	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The email subject */
    @Column( name = "email_subject", nullable = true)
    private String emailSubject;


    /** The from email ids */
    @Column( name = "from_email", nullable = false)
    private String fromEmail;

    /** The email. */
    @Column( name = "email_body", nullable = true )
    private String emailBody;
    
    @Column( name = "received_datetime", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime receivedDateTime;
    
    /** The email. */
    @Column( name = "scrapped_folder_name", nullable = false )
    private String scrappedFolderName;
    
    /** The email. */
    @Column( name = "mailbox_username", nullable = false )
    private String mailboxUserName;

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail the fromEmail to set
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * @return the emailBody
	 */
	public String getEmailBody() {
		return emailBody;
	}

	/**
	 * @param emailBody the emailBody to set
	 */
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	/**
	 * @return the receivedDateTime
	 */
	public DateTime getReceivedDateTime() {
		return receivedDateTime;
	}

	/**
	 * @param receivedDateTime the receivedDateTime to set
	 */
	public void setReceivedDateTime(DateTime receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}


	/**
	 * @return the scrappedFolderName
	 */
	public String getScrappedFolderName() {
		return scrappedFolderName;
	}

	/**
	 * @param scrappedFolderName the scrappedFolderName to set
	 */
	public void setScrappedFolderName(String scrappedFolderName) {
		this.scrappedFolderName = scrappedFolderName;
	}

	/**
	 * @return the mailboxUserName
	 */
	public String getMailboxUserName() {
		return mailboxUserName;
	}

	/**
	 * @param mailboxUserName the mailboxUserName to set
	 */
	public void setMailboxUserName(String mailboxUserName) {
		this.mailboxUserName = mailboxUserName;
	}
	
    
}
