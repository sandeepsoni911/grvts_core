package com.owners.gravitas.business;

import java.util.Set;

import javax.mail.Message;

import com.owners.gravitas.config.NotificationEmailConfig;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.AffiliateEmailValidationException;

/**
 * The Interface AffiliateEmailBusinessService.
 *
 * @author vishwanathm
 */
public interface AffiliateEmailBusinessService {

    /**
     * Scrap hubzu email message by reading the plain text content from email
     * message
     * and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    GenericLeadRequest scrapeHubzuEmailMessage( Message message );

    /**
     * Scrap owners email message by reading the plain text content from email
     * message
     * and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    GenericLeadRequest scrapeOwnersEmailMessage( Message message );

    /**
     * Scrap owners valuations email message by reading the plain text content
     * from email
     * message
     * and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    GenericLeadRequest scrapeValuationsEmailMessage( Message message );

    /**
     * Scrape owners seller lead email message by reading the plain text content
     * from email
     * message and create seller lead in to CRM.
     *
     * If scraping is success the message is attached with provided label, on
     * failure for parsing and validation the email notification is triggered.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    GenericLeadRequest scrapeOwnersSellerLeadEmailMessage( final Message message );

	/**
	 * Add label to message for given label name(create if label/folder not
	 * exists).
	 *
	 * @param message
	 *            the message
	 * @param labelName
	 *            the folder name
	 */
	void addLabelToMessage( String labelName, Message message );

	/**
	 * Handle email parsing error.
	 *
	 * @param errorId
	 *            the error id
	 * @param affEmailParsingExp
	 *            the aff email parsing exp
	 * @param emailConfig
	 *            the email config
	 */
	void handleEmailParsingError( String errorId, AffiliateEmailParsingException affEmailParsingExp,
			NotificationEmailConfig emailConfig );

	/**
	 * Handle email validation error.
	 *
	 * @param errorId
	 *            the error id
	 * @param validationException
	 *            the validation exception
	 * @param emailConfig
	 *            the email config
	 */
	void handleEmailValidationError( String errorId, AffiliateEmailValidationException validationException,
			NotificationEmailConfig emailConfig );

	/**
	 * Gets the hubzu subjects.
	 *
	 * @return the hubzu subjects
	 */
	Set< String > getHubzuSubjects();

	/**
	 * Gets the owners subjects.
	 *
	 * @return the owners subjects
	 */
	Set< String > getOwnersSubjects();

	/**
     * Gets the failure logger.
     *
     * @param message
     *            the message
     * @param exception
     *            the exception
     * @return the failure logger
     */
    LeadEmailParsingLog getFailureLogger( Message message, Exception exception );

    /**
     * Gets the success logger.
     *
     * @param message
     *            the message
     * @param leadRequest
     *            the lead request
     * @return the success logger
     */
    LeadEmailParsingLog getSuccessLogger( Message message, GenericLeadRequest leadRequest );

}
