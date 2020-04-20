package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.Constants.GRAVITAS_MORTGAGE_LEAD_NOTIFICATION;
import static com.owners.gravitas.constants.Constants.NOT_AVAILABLE;
import static com.owners.gravitas.constants.Constants.YES;
import static com.owners.gravitas.constants.Constants.NO;
import static com.owners.gravitas.constants.NotificationParameters.INTERESTED_ZIPS;
import static com.owners.gravitas.constants.NotificationParameters.INTERESTED_IN_FINANCING;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_FIRST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_LAST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.PHONE;
import static com.owners.gravitas.constants.NotificationParameters.CREATED_DATE;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.PROPERTY_ZIP;
import static com.owners.gravitas.constants.NotificationParameters.PROPERTY_ADDRESS;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_SOURCE;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_RECORD_TYPE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * Notification Builder for Finance Interested lead Notifications
 * 
 * @author sandeepsoni
 *
 */
@Component("mortgageLeadNotificationBuilder")
public class MortgageLeadNotificationBuilder extends AbstractBuilder<LeadSource, EmailNotification> {

	/** The from email address */
	//@Value(value = "${notify.finance.interested.lead.from.email}")
	private String fromEmailAddress;

	/** The to email address. */
	//@Value(value = "${notify.finance.interested.lead.to.email}")
	private String toEmailAddress;

	/** The bcc email address. */
	//@Value(value = "${notify.finance.interested.lead.bcc.email}")
	private String bccEmailAddress;

	/** The reply to email address. */
	//@Value(value = "${notify.finance.interested.lead.replyTo.email}")
	private String replyToEmailAddress;

	@Override
	public EmailNotification convertTo(LeadSource source, EmailNotification destination) {
		EmailNotification emailNotification = destination;
		if (source != null) {
			if (emailNotification == null) {
				emailNotification = new EmailNotification();
			}
			final Email email = new Email();
			email.setFromEmail(fromEmailAddress);
			emailNotification.setMessageTypeName(GRAVITAS_MORTGAGE_LEAD_NOTIFICATION);
			emailNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			emailNotification.setClientId(NOTIFICATION_CLIENT_ID);

			final Map<String, String> parameterMap = new HashMap<>();
			parameterMap.put(LEAD_FIRST_NAME, formatName(source.getFirstName()));
			parameterMap.put(LEAD_LAST_NAME, formatName(source.getLastName()));
			parameterMap.put(PHONE, StringUtils.isNotEmpty(source.getPhone()) ? source.getPhone() : NOT_AVAILABLE);
			parameterMap.put(LEAD_EMAIL, source.getEmail());
			parameterMap.put(INTERESTED_IN_FINANCING, source.isInterestedInFinancing() ? YES : NO);
			parameterMap.put(CREATED_DATE, formatDate(source.getCreatedDateTime()));
			parameterMap.put(PROPERTY_ZIP, getPropertyZipFromAddress(source.getPropertyAddress()));
			parameterMap.put(INTERESTED_ZIPS,
					StringUtils.isNotEmpty(source.getInterestedZipcodes()) ? source.getInterestedZipcodes()
							: NOT_AVAILABLE);
			parameterMap.put(PROPERTY_ADDRESS,
					StringUtils.isNotEmpty(source.getPropertyAddress()) ? source.getPropertyAddress() : NOT_AVAILABLE);
			parameterMap.put(LEAD_SOURCE, source.getSource());
			parameterMap.put(LEAD_RECORD_TYPE, source.getRecordType());
			email.setParameterMap(parameterMap);

			final EmailRecipients recipients = new EmailRecipients();
			final List<String> toList = new ArrayList<>();
			toList.add(toEmailAddress);
			recipients.setToList(toList);
			email.setRecipients(recipients);
			email.setReplyToEmail(replyToEmailAddress);

			final List<String> bccList = new ArrayList<>();
			bccList.add(bccEmailAddress);
			recipients.setBccList(bccList);

			emailNotification.setEmail(email);
		}
		return emailNotification;
	}

	/**
	 * To format date
	 * 
	 * @param createdDateTime
	 * @return
	 */
	private String formatDate(DateTime createdDateTime) {
		String formattedDate = NOT_AVAILABLE;
		if (createdDateTime != null) {
			DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
			formattedDate = dtfOut.print(createdDateTime);
		}
		return formattedDate;
	}

	/**
	 * To get property zip from address
	 * 
	 * @param propertyAddress
	 * @return
	 */
	private String getPropertyZipFromAddress(String propertyAddress) {
		String zip = NOT_AVAILABLE;
		if (StringUtils.isNotEmpty(propertyAddress)) {
			String address = (propertyAddress.length() > 5) ? propertyAddress.trim()
					.substring(propertyAddress.trim().length() - 5, propertyAddress.trim().length()) : null;
			zip = (address != null && StringUtils.isNumeric(address)) ? address : NOT_AVAILABLE;
		}
		return zip;
	}

	/**
	 * This method converts {@link EmailNotification} to {@link GenericLeadRequest}.
	 *
	 * @param source
	 *            {@link EmailNotification}.
	 *
	 * @param destination
	 *            {@link GenericLeadRequest}.
	 *
	 * @return returns instance of {@link GenericLeadRequest}.
	 */
	@Override
	public LeadSource convertFrom(EmailNotification destination, LeadSource source) {
		throw new UnsupportedOperationException("convertFrom operation is not supported");
	}

	/**
	 * Capitalizes the first letter of name.
	 *
	 * @param request
	 *            the LeadRequest
	 * @return the string
	 */
	private String formatName(final String request) {
		String formattedName = NOT_AVAILABLE;
		if (StringUtils.isNotBlank(request)) {
			formattedName = Character.toUpperCase(request.charAt(0)) + request.substring(1);
		}
		return formattedName;
	}
}
