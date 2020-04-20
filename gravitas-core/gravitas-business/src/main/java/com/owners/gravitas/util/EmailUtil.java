package com.owners.gravitas.util;


import java.util.Enumeration;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.constants.Constants;

/**
 * Utility class for email scrapping
 * 
 * @author sandeepsoni
 *
 */
@Component("emailUtil")
public class EmailUtil {

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	/** The Constant REGEX_NBSP. */
	private static final String REGEX_NBSP = "\u00A0";
	
	/** The Constant REGEX_NBSP. */
	private static final String SUBJECT = "SUBJECT";

	/**
	 * To get senders email address
	 * 
	 * @param message
	 * @return
	 */
	public String getSenderAddress(Message message) {
		String fromEmail = null;
		try {
			Address[] fromAddresses = message.getFrom();
			if (fromAddresses == null || fromAddresses.length == 0) {
				logger.error("From email address not present");
			}
			InternetAddress fromAddress = (InternetAddress) fromAddresses[0];
			fromEmail = fromAddress.getAddress();

		} catch (MessagingException me) {
			logger.error("Exception while retriving from email address", me.getMessage());
		}
		return fromEmail;
	}

	/**
	 * Gets the message string plain.
	 *
	 * @param source
	 *            the source
	 * @return the message string plain
	 */
	public String getMessageStringPlain(final String source) {
		String messageBody = getMessageStringHTML(
				StringEscapeUtils.unescapeHtml4(source.replaceAll(Constants.REG_EXP_HTML_TAGS, StringUtils.EMPTY)));
		if(messageBody != null && messageBody.length() > 1000) {
			messageBody = messageBody.substring(0, 1000);
		}
		return messageBody;
	}

	/**
	 * Gets the message string html.
	 *
	 * @param source
	 *            the source
	 * @return the message string html
	 */
	protected String getMessageStringHTML(final String source) {
		return source.replaceAll(Constants.REG_EXP_BLANK_SPACES, Constants.BLANK_SPACE)
				.replaceAll(REGEX_NBSP, StringUtils.EMPTY).trim();
	}

	/**
	 * provide user name
	 * 
	 * @param source
	 * @return
	 */
	public String getUserName(Message source) {
		String username = null;
		if (source != null && source.getFolder() != null && source.getFolder().getStore() != null) {
			username = source.getFolder().getStore().getURLName().getUsername();
		}
		return username;
	}

	/**
	 * To get subject line
	 * @param message
	 * @return
	 */
	public String getEmailSubject(Message message) {
		String subject = null;

		if (message != null) {
			try {
				subject = message.getSubject();
				if (subject == null) {
					Enumeration headerEnumeration = message.getAllHeaders();
					while (headerEnumeration.hasMoreElements()) {
						Header header = (Header) headerEnumeration.nextElement();
						if (header.getName().equalsIgnoreCase(SUBJECT)) {
							subject = header.getValue();
							break;
						}
					}
				}
				if (subject != null && subject.length() > 200) {
					subject = subject.substring(0, 200);
				}

			} catch (MessagingException e) {
				logger.error("Exception while fetching subject from header :" + e.getMessage());
			}
		}
		return subject;
	}

}
