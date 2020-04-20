package com.owners.gravitas.business.builder;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.owners.gravitas.business.builder.scraping.MessageStringBuilder;
import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.util.EmailUtil;

/**
 * Builder for
 * 
 * @author sandeepsoni
 *
 */
@Component("emailScrappingDetailBuilder")
public class EmailScrappingDetailBuilder extends AbstractBuilder<Message, EmailScrappingDetails> {

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(EmailScrappingDetailBuilder.class);

	/** The message string builder. */
	@Autowired
	private MessageStringBuilder messageStringBuilder;
	
	/** The Email Utility. */
	@Autowired
	EmailUtil emailUtil;


	@Override
	public EmailScrappingDetails convertTo(Message source, EmailScrappingDetails destination) {
		EmailScrappingDetails emailScrappingDetails = null;
		if (source != null) {
			emailScrappingDetails = new EmailScrappingDetails();
			final String messageTextPart = messageStringBuilder.convertTo(source);
			
			try {
				emailScrappingDetails.setEmailBody(emailUtil.getMessageStringPlain(messageTextPart));
				emailScrappingDetails.setEmailSubject(emailUtil.getEmailSubject(source));
				DateTime receivedDate = new DateTime(source.getReceivedDate());
				emailScrappingDetails.setReceivedDateTime(receivedDate);
				emailScrappingDetails.setFromEmail(emailUtil.getSenderAddress(source));
				emailScrappingDetails.setScrappedFolderName(source.getFolder().getFullName());
				emailScrappingDetails.setMailboxUserName(emailUtil.getUserName(source));
			} catch (MessagingException e) {
				throw new ApplicationException ("Exception occured while scrapping email", e);
			}
		}
		return emailScrappingDetails;
	}


	@Override
	public Message convertFrom(EmailScrappingDetails source, Message destination) {
		 throw new UnsupportedOperationException( "convertFrom is not supported" );
	}
	

	

	

}
